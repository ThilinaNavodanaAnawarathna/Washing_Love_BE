package com.sliit.washing_love_be.service.impl;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.sliit.washing_love_be.client.DamageDetectionServiceClient;
import com.sliit.washing_love_be.dto.*;
import com.sliit.washing_love_be.entity.Booking;
import com.sliit.washing_love_be.enumz.BookingStatus;
import com.sliit.washing_love_be.service.*;
import com.sliit.washing_love_be.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DamageDetectionServiceImpl implements DamageDetectionService {
    private final BookingService bookingService;
    private final SparePartService sparePartService;
    private final VehicleService vehicleService;
    private final DamageDetectionServiceClient damageDetectionServiceClient;
    private final ReportService reportService;
    @Value("${upload.path}")
    private String uploadDirectory;
    private String part = "";

    public DamageDetectionServiceImpl(BookingService bookingService, SparePartService sparePartService, VehicleService vehicleService, DamageDetectionServiceClient damageDetectionServiceClient, ReportService reportService) {
        this.bookingService = bookingService;
        this.sparePartService = sparePartService;
        this.vehicleService = vehicleService;
        this.damageDetectionServiceClient = damageDetectionServiceClient;
        this.reportService = reportService;
    }

    @Override
    public Object detectDamages(MultipartFile front, MultipartFile back, MultipartFile left, MultipartFile right, Long bookingId) throws Exception {
        Booking booking = bookingService.findBookingById(bookingId);
        if (booking == null)
            throw new RuntimeException("Invalid booking details");
        VehicleDto vehicleDto = vehicleService.findById(booking.getVehicle().getId());
        if (vehicleDto == null)
            throw new RuntimeException("Invalid vehicle details");

        DamageDetectResponse frontReport = damageDetectionServiceClient.checkDamages(front);
        DamageDetectResponse backReport = damageDetectionServiceClient.checkDamages(back);
        DamageDetectResponse leftReport = damageDetectionServiceClient.checkDamages(left);
        DamageDetectResponse rightReport = damageDetectionServiceClient.checkDamages(right);

        Map result = new HashMap<>();
        result.put("frontReport", frontReport);
        result.put("backReport", backReport);
        result.put("leftReport", leftReport);
        result.put("rightReport", rightReport);
        BookingDto bookingDto = bookingService.updateBookingStatus(booking.getId(), BookingStatus.COMPLETED);
        generateReport(frontReport, backReport, leftReport, rightReport, bookingDto);
        return result;
    }

    private String saveFile(MultipartFile file, String position, Long bookingId) throws Exception {
        Path filePath = null;
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = "";
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0) {
            fileExtension = fileName.substring(dotIndex);
        }
        String newFileName = bookingId + "_" + position + fileExtension;
        filePath = Paths.get(uploadDirectory, newFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return newFileName;
    }

    private void generateReport(DamageDetectResponse frontReport,
                                DamageDetectResponse backReport,
                                DamageDetectResponse leftReport,
                                DamageDetectResponse rightReport, BookingDto booking) throws Exception {
        File file = new File(uploadDirectory + "/" + booking.getId() + "_report.pdf");

        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(file));
        Document document = new Document(pdfDocument);
        Paragraph paragraph = new Paragraph(Utils.htmlTemplate
                .replace("r_id", booking.getId().toString())
                .replace("r_time", booking.getStartTime().toString())
                .replace("r_date", booking.getDate().toString()));

        document.add(paragraph);

//        Front ReportAdding
        byte[] imageBytes = Base64.decodeBase64(frontReport.getImageBase64());
        Image image = new Image(ImageDataFactory.create(imageBytes));
        image.setHeight(200);
        image.setWidth(200);
        document.add(image);
        Arrays.stream(frontReport.getDamageParts()).forEach(name ->
                part = part.concat(name + " - " + "2500LKR\n")
        );
        Paragraph paragraph1 = new Paragraph(part + "Back view");
        document.add(paragraph1);
        part = "";

        //        Back ReportAdding
        byte[] imageBytes2 = Base64.decodeBase64(backReport.getImageBase64());
        Image image2 = new Image(ImageDataFactory.create(imageBytes2));
        image2.setHeight(200);
        image2.setWidth(200);
        document.add(image2);
        Arrays.stream(backReport.getDamageParts()).forEach(name ->
                part = part.concat(name + " - " + "2500LKR\n")
        );
        Paragraph paragraph2 = new Paragraph(part + "Left view");
        document.add(paragraph2);
        part = "";

        //        Left ReportAdding
        byte[] imageBytes3 = Base64.decodeBase64(leftReport.getImageBase64());
        Image image3 = new Image(ImageDataFactory.create(imageBytes3));
        image3.setHeight(200);
        image3.setWidth(200);
        document.add(image3);
        Arrays.stream(leftReport.getDamageParts()).forEach(name ->
                part = part.concat(name + " - " + "2500LKR\n")
        );
        Paragraph paragraph3 = new Paragraph(part + "Right view");
        document.add(paragraph3);
        part = "";

        //        Left ReportAdding
        byte[] imageBytes4 = Base64.decodeBase64(rightReport.getImageBase64());
        Image image4 = new Image(ImageDataFactory.create(imageBytes4));
        image4.setHeight(200);
        image4.setWidth(200);
        document.add(image4);
        Arrays.stream(rightReport.getDamageParts()).forEach(name ->
                part = part.concat(name + " - " + "2500LKR\n")
        );
        Paragraph paragraph4 = new Paragraph(part);
        document.add(paragraph4);
        part = "";

        Paragraph paragraph5 = new Paragraph(Utils.endOfReport);
        document.add(paragraph5);

        document.close();

        UserBasicDetails userBasicDetails = UserBasicDetails.builder().id(booking.getUser().getId()).build();
        ReportDto reportDto = ReportDto.builder().booking(booking).url(booking.getId() + "_report.pdf").user(userBasicDetails).build();
        reportService.save(reportDto);
    }
}
