package ru.csc.android_course.qurbonzoda.weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  WeatherListFragment listFragment;
  EditText cityNameEditText;
  Button cityAddButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    cityNameEditText = (EditText) findViewById(R.id.city_name_edit_text);
    cityAddButton = (Button) findViewById(R.id.city_add_button);

    getSupportFragmentManager()
        .beginTransaction()
        .replace(R.id.fragment_main_container, (listFragment = WeatherListFragment.newInstance(null, null)))
        .commit();

    cityAddButton.setOnClickListener(this);
    cityNameEditText.setOnEditorActionListener((v, actionId, event) -> {
      if (actionId == EditorInfo.IME_ACTION_DONE) {
        cityAddButton.callOnClick();
        return true;
      }
      return true;
    });
  }

  @Override
  public void onClick(View v) {
    listFragment.addCity(cityNameEditText.getText().toString());
  }
}
