package com.example.myapplication;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.MediaController;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class CargaVideo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carga_video);
        //Configuramos el video para la reproduccion local
        VideoView videoView = findViewById(R.id.videoView);
        //Construimos una URI del video
        String videoPath = "android.resource://"+ getPackageName() + "/" + R.raw.video;
        //Convierto la cadena en un URI
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        //Agrego controles de reproducción del video
        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        //Vinculamos el controlador de video para mostrar en la app
        mediaController.setAnchorView(videoView);
        videoView.start();



        //Configuración de reproducción del sonido MP3
        findViewById(R.id.reproducir).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creo un MediaPlayer para reproducir el sonido
                MediaPlayer mediaPlayer = MediaPlayer.create(CargaVideo.this, R.raw.sonido);
                //Inicio la reproducción
                mediaPlayer.start();
                //Listener para liberar recursos cuando sonido termine
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.release(); //Libero recursos cuando el sonido termine
                    }
                });
            }
        });

    }
}

