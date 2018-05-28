package com.example.lamlethanhthe.studyhelper;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lamlethanhthe.studyhelper.DataModules.DataManager;
import com.example.lamlethanhthe.studyhelper.DataModules.Location;
import com.example.lamlethanhthe.studyhelper.MapsModules.DirectionFinder;
import com.example.lamlethanhthe.studyhelper.MapsModules.DirectionFinderListener;
import com.example.lamlethanhthe.studyhelper.MapsModules.MapAlgos;
import com.example.lamlethanhthe.studyhelper.MapsModules.Route;
import com.example.lamlethanhthe.studyhelper.AdapterModules.LocationAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener, AdapterView.OnItemSelectedListener {

    private GoogleMap mMap;

    private List<Polyline> path = new ArrayList<>();

    DataManager dat;;
    private ArrayList<Location> locations;

    private LinearLayout layoutChoose, layoutMaps, layoutAdd;
    private EditText edtSearchLoc, edtLocName;

    private TextView txtOrig, txtDest, txtNewLocAddress;
    private Location orig, dest;

    private Marker origMarker, destMarker, pivot;

    private ListView listLoc;
    private LocationAdapter locAdapter;

    private Button backFromAdd, add;

    private Spinner spinner;
    private String spPivot;

    private Button findPath;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initComponents();
    }

    private void initComponents() {
        layoutChoose = (LinearLayout)findViewById(R.id.choose);
        layoutChoose.setVisibility(View.GONE);

        layoutAdd = (LinearLayout)findViewById(R.id.add);
        layoutAdd.setVisibility(View.GONE);

        layoutMaps = (LinearLayout)findViewById(R.id.mapContainer);
        layoutMaps.setVisibility(View.VISIBLE);

        dat = new DataManager(MapsActivity.this);
        locations = dat.getLocations();

        txtDest = (TextView)findViewById(R.id.txtDest);
        txtOrig = (TextView)findViewById(R.id.txtOrig);

        txtOrig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forDest = false;
                openLocLists();
            }
        });

        txtDest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forDest = true;
                openLocLists();
            }
        });

        origMarker = destMarker = null;

        dest = getFirstTestSite();
        orig = getFirstOvernightSite();

        findPath = (Button)findViewById(R.id.btnFindPath);
        load = (ProgressBar)findViewById(R.id.barLoading);
        load.setVisibility(View.GONE);
        findPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procFindingPath();
            }
        });

        initChooseLayout();
        initAddLayout();
    }

    private void procFindingPath() {
        if (dest == null) {
            Toast.makeText(MapsActivity.this, getResources().getString(R.string.map_input_dest), Toast.LENGTH_SHORT).show();
            return;
        }

        if (orig == null) {
            Toast.makeText(MapsActivity.this, getResources().getString(R.string.map_input_orgn), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mMap.clear();
            new DirectionFinder(MapsActivity.this, orig.getPos(), dest.getPos()).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initAddLayout() {
        backFromAdd = (Button)findViewById(R.id.btnBackFromAdd);
        add = (Button)findViewById(R.id.btnAddLoc);
        edtLocName = (EditText)findViewById(R.id.edtNewLocName);
        txtNewLocAddress = (TextView)findViewById(R.id.txtNewLocAddress);
        spinner = (Spinner)findViewById(R.id.spinnerLocType);

        backFromAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMaps();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = edtLocName.getText().toString();
                Location newLoc = new Location(DataManager.curUser, pivot.getPosition(), newName, pivot.getTitle(), Location.fromString(spPivot));
                locations.add(newLoc);
                locAdapter.notifyDataSetChanged();
                Toast.makeText(MapsActivity.this, getResources().getString(R.string.map_add_loc), Toast.LENGTH_SHORT).show();
                backToMaps();
            }
        });

        spinner.setOnItemSelectedListener(MapsActivity.this);

        List<String> categories = new ArrayList<String>();
        categories.add("Địa điểm thi");
        categories.add("Nơi nghỉ");
        categories.add("Khác");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        spinner.setAdapter(dataAdapter);
    }

    private void initChooseLayout() {
        edtSearchLoc = (EditText)findViewById(R.id.edtSearchLoc);
        listLoc = (ListView)findViewById(R.id.listLoc);

        if (locations == null)
            locations = new ArrayList<>();
        locAdapter = new LocationAdapter(MapsActivity.this, locations);
        listLoc.setAdapter(locAdapter);

        edtSearchLoc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locAdapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listLoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location tmp = (Location)listLoc.getItemAtPosition(position);

                if (forDest) {
                    dest = tmp;
                    initDest();
                }
                else {
                    orig = tmp;
                    initOrig();
                }

                backToMaps();
            }
        });
    }

    private Location getFirstOvernightSite() {
        if (locations != null && locations.size() > 0) {
            for (Location loc : locations) {
                if (loc.getType() == Location.LocType.overnightSite) {
                    return loc;
                }
            }
        }
        return null;
    }

    private Location getFirstTestSite() {
        if (locations != null && locations.size() > 0) {
            for (Location loc : locations) {
                if (loc.getType() == Location.LocType.testSite) {
                    return loc;
                }
            }
        }
        return null;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add units marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                String s = MapAlgos.getAddressFromLatLng(MapsActivity.this, latLng);
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_SHORT).show();

                MarkerOptions mkops = new MarkerOptions().title(s).position(latLng).draggable(true);
                mMap.addMarker(mkops);
            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                String s = MapAlgos.getAddressFromLatLng(MapsActivity.this, marker.getPosition());
                marker.setTitle(s);
                Toast.makeText(MapsActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for (Location l : locations) {
                    if (l.getPos().latitude == marker.getPosition().latitude && l.getPos().longitude == marker.getPosition().longitude) {
                        Toast.makeText(MapsActivity.this, getResources().getString(R.string.map_loc_available) + ": " + l.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                pivot = marker;
                openAddLoc();
                txtNewLocAddress.setText(pivot.getTitle());
                return true;
            }
        });

        initDest();
        initOrig();
    }

    private void initOrig() {
        if (orig == null) {
            txtOrig.setHint(getResources().getString(R.string.map_hint_orgn));
            return;
        }

        if (origMarker != null)
            origMarker.remove();

        MarkerOptions mkops = new MarkerOptions().title(orig.getName()).position(orig.getPos()).draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        origMarker = mMap.addMarker(mkops);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(orig.getPos(), 16));

        txtOrig.setText(orig.getName());
    }

    private void initDest() {
        if (dest == null) {
            txtDest.setHint(getResources().getString(R.string.map_hint_dest));
            return;
        }

        if (destMarker != null)
            destMarker.remove();

        MarkerOptions mkops = new MarkerOptions().title(dest.getName()).position(dest.getPos()).draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        destMarker = mMap.addMarker(mkops);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest.getPos(), 16));

        txtDest.setText(dest.getName());
    }

    @Override
    public void onDirectionFinderStart() {
        load.setVisibility(View.VISIBLE);
        if (origMarker != null)
            origMarker.remove();

        if (destMarker != null)
            destMarker.remove();

        if (path != null) {
            for (Polyline polyline : path) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        path = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 15));

            ((TextView)findViewById(R.id.dist)).setText(route.distance.text);
            ((TextView)findViewById(R.id.duration)).setText(route.duration.text);

            origMarker = mMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .draggable(false));

            destMarker = mMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation).icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .draggable(false));

            PolylineOptions polylineOps = new PolylineOptions().geodesic(true).color(Color.BLUE).width(10);

            for (int i = 0; i < route.points.size(); ++i) {
                polylineOps.add(route.points.get(i));
            }

            path.add(mMap.addPolyline(polylineOps));

            load.setVisibility(View.GONE);
        }
    }

    private boolean forDest = false;

    private void openLocLists() {
        layoutMaps.setVisibility(View.GONE);
        layoutAdd.setVisibility(View.GONE);
        layoutChoose.setVisibility(View.VISIBLE);
    }

    public void backToMaps() {
        layoutChoose.setVisibility(View.GONE);
        layoutAdd.setVisibility(View.GONE);
        layoutMaps.setVisibility(View.VISIBLE);
    }

    public void openAddLoc() {
        layoutChoose.setVisibility(View.GONE);
        layoutAdd.setVisibility(View.VISIBLE);
        layoutMaps.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spPivot = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(MapsActivity.this, getResources().getString(R.string.map_input_locType), Toast.LENGTH_SHORT).show();
    }

    public void BackFromLocList(View view) {
        backToMaps();
    }

    @Override
    public void onBackPressed() {
        dat.updateLocations_Evil(locations);
        super.onBackPressed();
    }
}