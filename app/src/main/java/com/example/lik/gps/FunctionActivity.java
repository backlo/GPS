package com.example.lik.gps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import android.widget.Toast;


public class FunctionActivity extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    AudioManager audioManager;
    MediaPlayer mp;
    final Context context = this;
    Intent lockService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        showSettingAlert();

        adapter = new CustomAdapter();
        listView = (ListView) findViewById(R.id.listView1);
        listView.setAdapter(adapter);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        lockService = new  Intent(getApplicationContext(), LockService.class);

        mp = new MediaPlayer();
        mp = MediaPlayer.create(FunctionActivity.this, R.raw.test);
        mp.setLooping(true);

        adapter.add("안녕하세요 스미~입니다.원하시는 부분을 골라주세요 \n 1번 위치 2번 정지 3번 정지해제 4번 노래모드 5번 진동 6번 소리 \n (노래모드 설정시 노래정지하면 정지 됩니다.)", 0);

        findViewById(R.id.sendbutton).setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) findViewById(R.id.editText1);
                String inputValue = editText.getText().toString();
                editText.setText("");
                refresh(inputValue, 1);

                if (inputValue.equals("위치") || inputValue.equals("1번") || inputValue.equals("위치모드")) {
                    adapter.add("위치정보입니다", 0);
                    Intent intent = new Intent(getApplicationContext(), getGPS.class);
                    startActivity(intent);
                } else if (inputValue.equals("정지") || inputValue.equals("2번") || inputValue.equals("정지모드")) {
                    adapter.add("정지모드로 설정되었습니다.", 0);
                    startService(lockService);
                    Toast.makeText(getApplicationContext(), "정지모드 실행", Toast.LENGTH_SHORT).show();
                } else if (inputValue.equals("정지해제") || inputValue.equals("3번") || inputValue.equals("정지해제모드")) {
                    adapter.add("정지해제모드로 설정되었습니다", 0);
                    stopService(lockService);
                    Toast.makeText(getApplicationContext(), "정지해제모드 실행", Toast.LENGTH_SHORT).show();
                } else if (inputValue.equals("노래모드") || inputValue.equals("4번") || inputValue.equals("노래") || inputValue.equals("노래정지")) {
                    if (inputValue.equals("노래모드") || inputValue.equals("4번") || inputValue.equals("노래")) {
                        adapter.add("노래모드로 설정되었습니다", 0);
                        mp.start();
                        Toast.makeText(getApplicationContext(), "노래모드 실행", Toast.LENGTH_SHORT).show();
                    } else if (inputValue.equals("노래정지")) {
                        adapter.add("노래정지합니다.", 0);
                        mp.pause();
                        Toast.makeText(getApplicationContext(), "노래정지모드 실행", Toast.LENGTH_SHORT).show();
                    }
                } else if (inputValue.equals("진동") || inputValue.equals("5번") || inputValue.equals("진동모드")) {
                    adapter.add("진동모드로 설정되었습니다", 0);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    Toast.makeText(getApplicationContext(), "진동모드 실행", Toast.LENGTH_SHORT).show();
                } else if (inputValue.equals("소리") || inputValue.equals("6번") || inputValue.equals("소리모드")) {
                    adapter.add("소리모드로 설정되었습니다", 0);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), audioManager.FLAG_PLAY_SOUND);
                    Toast.makeText(getApplicationContext(), "소리모드 실행", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.add("잘못입력하였습니다. 다시 입력해주십시요", 0);
                }

            }
        });
    }

    public void refresh(String inputValue, int str) {
        adapter.add(inputValue, str);
        adapter.notifyDataSetChanged();
    }

    public void showSettingAlert() {

        String gpsEnabled = android.provider.Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!(gpsEnabled.matches(".*gps.*") && gpsEnabled.matches(".*network.*"))) {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("GPS 설정").setMessage("GPS 셋팅이 되어있지 않습니다.\n 위치권한설정하시기 바랍니다").setCancelable(false)
                    .setPositiveButton("하기", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }).setNegativeButton("안하기", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertdialog = alert.create();
            alertdialog.show();
        } else if ((gpsEnabled.matches(".*gps.*") && gpsEnabled.matches(".*network.*"))) {
        }

    }

}
