package tw.com.pcschool.dd2018013101;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);
    }
    public void click1(View v)
    {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 123);
    }

    public void click2(View v)
    {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(getExternalFilesDir("PHOTO"), "myphoto.jpg");
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        startActivityForResult(it, 456);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle pBundle = data.getExtras();
                Bitmap bmp = (Bitmap) pBundle.get("data");
                img.setImageBitmap(bmp);
            }
        }
        if (requestCode == 456)
        {
            if (resultCode == RESULT_OK)
            {
                File f = new File(getExternalFilesDir("PHOTO"), "myphoto.jpg");
                try {
                    InputStream is = new FileInputStream(f);
                    Bitmap bmp = getFitImage(is);
                    img.setImageBitmap(bmp);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public static Bitmap getFitImage(InputStream is)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeStream(is, null, options);
        System.gc();
        return bmp;

    }
}
