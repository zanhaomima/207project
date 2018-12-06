
import javax.swing.JLabel;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
class MatchTemplateDemoRun  {
    Boolean use_mask = false;
    Mat img = new Mat(), templ = new Mat();
    Mat mask = new Mat();
    int match_method;
    JLabel imgDisplay = new JLabel(), resultDisplay = new JLabel();
    public void run(String[] args) {
        if (args.length < 2) {
            System.out.println("Not enough parameters");
            System.out.println("Program arguments:\n<image_name> <template_name> [<mask_name>]");
            System.exit(-1);
        }
        img = Imgcodecs.imread(args[0], Imgcodecs.IMREAD_COLOR);
        templ = Imgcodecs.imread(args[1], Imgcodecs.IMREAD_COLOR);
        if (args.length > 2) {
            use_mask = true;
            mask = Imgcodecs.imread(args[2], Imgcodecs.IMREAD_COLOR);
        }
        if (img.empty() || templ.empty() || (use_mask && mask.empty())) {
            System.out.println("Can't read one of the images");
            System.exit(-1);
        }
        matchingMethod();
    }
    private void matchingMethod() {
        Mat result = new Mat();
        Mat img_display = new Mat();
        img.copyTo(img_display);
        int result_cols = img.cols() - templ.cols() + 1;
        int result_rows = img.rows() - templ.rows() + 1;
        result.create(result_rows, result_cols, CvType.CV_32FC1);
        Boolean method_accepts_mask = (Imgproc.TM_SQDIFF == match_method || match_method == Imgproc.TM_CCORR_NORMED);
        if (use_mask && method_accepts_mask) {
            Imgproc.matchTemplate(img, templ, result, match_method, mask);
        } else {
            Imgproc.matchTemplate(img, templ, result, match_method);
        }
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
        Point matchLoc;
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
            matchLoc = mmr.minLoc;
        } else {
            matchLoc = mmr.maxLoc;
        }
        System.out.println(matchLoc.x +" "+ templ.cols()+" "+ matchLoc.y +" "+ templ.rows());
    }
    
}
public class JumpNew {
    public static void main(String[] args) {
        // load the native OpenCV library
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        // run code
        String []a= {"/Users/jifengzheng/Desktop/Jump.png","/Users/jifengzheng/Desktop/Jm.png","/Users/jifengzheng/Desktop/Jm.png"};
        new MatchTemplateDemoRun().run(a);
    }
}