package arrow.mems.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCode {
  public static final String CHARSET = "UTF-8"; // or "ISO-8859-1"

  public static void main(String[] args) throws WriterException, IOException, NotFoundException {
    final String qrCodeData = "Hello World!";
    final String filePath = "QRCode.png";

    QRCode.createQRCode(qrCodeData, filePath, 200, 200);
    System.out.println("QR Code image created successfully!");
    System.out.println("Data read from QR Code: " + QRCode.readQRCode(filePath));
  }

  public static void createQRCode(String qrCodeData, String filePath, int qrCodeheight, int qrCodewidth) throws WriterException, IOException {
    final Map<EncodeHintType, Object> hintMap = new HashMap<EncodeHintType, Object>();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    final BitMatrix matrix =
        new MultiFormatWriter().encode(new String(qrCodeData.getBytes(QRCode.CHARSET), QRCode.CHARSET), BarcodeFormat.QR_CODE, qrCodewidth,
            qrCodeheight, hintMap);
    MatrixToImageWriter.writeToPath(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), (new File(filePath)).toPath());
  }

  public static String readQRCode(String filePath) throws FileNotFoundException, IOException, NotFoundException {
    final Map<DecodeHintType, Object> hintMap = new HashMap<DecodeHintType, Object>();
    // hintMap.put(DecodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    final BinaryBitmap binaryBitmap =
        new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
    final Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
    return qrCodeResult.getText();
  }

}
