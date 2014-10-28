package arrow.mems.service;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;


public class PrintQRCodeServiceTest {
  @Inject
  PrintQRCodeService QRCodeService;

  @Test
  public void testCreateQRCode() throws WriterException, IOException {
    final String QRCodeFolder = "/var/mems/QRCode";
    final String deviceCode = "1117002140000114000001";
    final String filePath = QRCodeFolder + "/" + deviceCode + ".png";
    this.QRCodeService.createQRCode(deviceCode, filePath, 150, 150);
    final File file = new File(filePath);
    Assert.assertTrue(file.isFile());
  }

  @Test
  public void testReadQRCode() throws WriterException, IOException, NotFoundException {
    final String QRCodeFolder = "/var/mems/QRCode";
    final String deviceCode = "1117002140000114000001";
    final String filePath = QRCodeFolder + "/" + deviceCode + ".png";
    final File file = new File(filePath);
    Assert.assertTrue(file.isFile());

    final String getData = this.QRCodeService.readQRCode(filePath);
    Assert.assertEquals(deviceCode, getData);
  }

}
