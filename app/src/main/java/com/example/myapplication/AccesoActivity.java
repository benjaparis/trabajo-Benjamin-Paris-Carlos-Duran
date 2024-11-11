package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.views.MapView;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

public class AccesoActivity extends AppCompatActivity {
    private MapView mapView;
    private Spinner mapTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);  // Este layout contiene el mapa y el spinner

        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        //punto de localización
        double IPSTLatitud = -33.4493141;
        double IPSTLongitud = -70.6624069;
        GeoPoint IPSTPoint = new GeoPoint(IPSTLatitud, IPSTLongitud);
        mapView.getController().setZoom(19.0);
        mapView.getController().setCenter(IPSTPoint);

        Marker marcadorSantoTomas = new Marker(mapView);
        marcadorSantoTomas.setPosition(IPSTPoint);
        marcadorSantoTomas.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marcadorSantoTomas.setTitle("IP Santo Tomas, Chile");
        marcadorSantoTomas.setSnippet("Lugar de destino");
        mapView.getOverlays().add(marcadorSantoTomas);

        double GLatitud = -33.45207563989927;
        double GLongitud = -70.5697170567791;
        GeoPoint GPoint = new GeoPoint(GLatitud, GLongitud);
        Marker marcadorG = new Marker(mapView);
        marcadorG.setPosition(GPoint);
        marcadorG.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marcadorG.setTitle("Mall plaza egaña");
        marcadorG.setSnippet("Su ubicacion");
        mapView.getOverlays().add(marcadorG);

        Polyline linea = new Polyline();
        linea.addPoint(IPSTPoint);
        linea.addPoint(GPoint);
        linea.setColor(0xFF0000FF);
        linea.setWidth(5);
        mapView.getOverlayManager().add(linea);

        // Configurar spinner para cambiar tipo de mapa
        mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa Normal", "Mapa de Transporte", "Mapa Topográfico"};
        ArrayAdapter<String> adapterMap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapterMap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapterMap);

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        break;
                    case 1:
                        mapView.setTileSource(new XYTileSource("PublicTransport", 0, 18, 256, ".png", new String[]{
                                "https://tile.memomaps.de/tilegen/"}));
                        break;
                    case 2:
                        mapView.setTileSource(new XYTileSource("USGS_Satellite", 0, 18, 256, ".png", new String[]{
                                "https://a.tile.opentopomap.org/",
                                "https://b.tile.opentopomap.org/",
                                "https://c.tile.opentopomap.org/"}));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Configurar botón para navegar a CargaVideo
        Button buttonAcceder = findViewById(R.id.Onclickacceder);
        buttonAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccesoActivity.this, CargaVideo.class);
                startActivity(intent);
            }
        });
    }
}
