package com.example.MireaProject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HardwareFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HardwareFragment extends Fragment implements SensorEventListener {

    private TextView azimuthTextView;
    private TextView pitchTextView;
    private TextView rollTextView;
    private TextView xyView;
    private TextView xzView;
    private TextView zyView;
    private TextView axisX;
    private TextView axisY;
    private TextView axisZ;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor mOrientation;
    private Sensor gravitySensor;

    private float xy_angle;
    private float xz_angle;
    private float zy_angle;

    private static final int REQUEST_CODE_PERMISSION_CAMERA = 100;
    private ImageView imageView;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;

    public Button button;
    public Button buttonRecord;
    public Button buttonStop;
    public Button buttonPlay;

    private static int MICROPHONE_PERMISSION_CODE = 200;

    MediaRecorder mediaRecorder;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HardwareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HardwareFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HardwareFragment newInstance(String param1, String param2) {
        HardwareFragment fragment = new HardwareFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_hardware, container, false);


        sensorManager =
                (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        accelerometerSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mOrientation = sensorManager
                .getDefaultSensor(Sensor.TYPE_ORIENTATION);
        gravitySensor = sensorManager.
                getDefaultSensor(Sensor.TYPE_GRAVITY);


        azimuthTextView = inflatedView.findViewById(R.id.textViewAccelerometerAzimuth);
        pitchTextView = inflatedView.findViewById(R.id.textViewAccelerometerPitch);
        rollTextView = inflatedView.findViewById(R.id.textViewAccelerometerRoll);

        xyView = (TextView) inflatedView.findViewById(R.id.textViewOrientationXY);
        xzView = (TextView) inflatedView.findViewById(R.id.textViewOrientationXZ);
        zyView = (TextView) inflatedView.findViewById(R.id.textViewOrientationYZ);

        axisX = (TextView) inflatedView.findViewById(R.id.textViewGravityX);
        axisY = (TextView) inflatedView.findViewById(R.id.textViewGravityY);
        axisZ = (TextView) inflatedView.findViewById(R.id.textViewGravityZ);

        sensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, mOrientation,
                SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(this, gravitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        imageView = inflatedView.findViewById(R.id.imageViewCamera);

        int cameraPermissionStatus =
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE_PERMISSION_CAMERA);
        }

        button = (Button) inflatedView.findViewById(R.id.buttonOpenCamera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null && isWork == true) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // генерирование пути к файлу на основе authorities
                    String authorities = getActivity().getApplicationContext().getPackageName() + ".fileprovider";
                    imageUri = FileProvider.getUriForFile(getActivity(), authorities, photoFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        buttonRecord = (Button) inflatedView.findViewById(R.id.buttonRecord);
        buttonStop = (Button) inflatedView.findViewById(R.id.buttonStop);
        buttonPlay = (Button) inflatedView.findViewById(R.id.buttonPlay);

        buttonRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mediaRecorder.setOutputFile(getRecordingFilePath());
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                try {
                    mediaRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaRecorder.start();

                Toast.makeText(getActivity(), "Recording is started", Toast.LENGTH_SHORT).show();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;

                Toast.makeText(getActivity(), "Recording is stopped", Toast.LENGTH_SHORT).show();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), HardwareAudioService.class);
                intent.putExtra("path", getRecordingFilePath());
                getActivity().startService(intent);
            }
        });

        if (isMicrophonePresent()){
            getMicrophonePermission();
        }

        return inflatedView;
    }

    private String getRecordingFilePath(){
        ContextWrapper contextWrapper = new ContextWrapper(getActivity().getApplicationContext());
        File musicDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file = new File(musicDirectory, "testRecordingFile" + ".mp3");
        return file.getPath();
    }

    private boolean isMicrophonePresent(){
        if (getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true;
        }
        else{
            return false;
        }
    }

    private void getMicrophonePermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float valueAzimuth = event.values[0];
            float valuePitch = event.values[1];
            float valueRoll = event.values[2];
            azimuthTextView.setText("Azimuth: " + valueAzimuth);
            pitchTextView.setText("Pitch: " + valuePitch);
            rollTextView.setText("Roll: " + valueRoll);
            Log.d("my_accelerometer", valueAzimuth + " " + valuePitch + " " + valueRoll);
        }
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            xy_angle = event.values[0];
            xz_angle = event.values[1];
            zy_angle = event.values[2];
            xyView.setText("XY: " + xy_angle);
            xzView.setText("XZ: " + xz_angle);
            zyView.setText("ZY: " + zy_angle);
            Log.d("my_orientation", xy_angle + " " + xz_angle + " " + zy_angle);
        }
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float valueX = event.values[0];
            float valueY = event.values[1];
            float valueZ = event.values[2];
            axisX.setText("X: " + valueX);
            axisY.setText("Y: " + valueY);
            axisZ.setText("Z: " + valueZ);
            Log.d("my_gravity", valueX + " " + valueY + " " + valueZ);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK) {
            imageView.setImageURI(imageUri);
        }
    }


    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION_CAMERA) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                isWork = true;
            } else {
                isWork = false;
            }
        }
    }
}


