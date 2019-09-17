package com.example.hardwaresensor

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventCallback
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //To access all the hardware services we need to access the sensor Manager
        val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        //Now to aceess all the types of a particular sensor. we do this
        val sensors = sm.getSensorList(Sensor.TYPE_ALL)
        sensors.forEach{
            Log.i("SENSORS", """
                ${it.name}
                ${it.vendor}  
                """".trimIndent())
        } //this it. vendor tell us about who has created the service

        //Now we will see how to access and use acceleratometer
        val accelerationSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) //This sensor measures the acceleration of the phone.

        val sensorListener = object : SensorEventCallback() {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                super.onAccuracyChanged(sensor, accuracy)
            }

            override fun onSensorChanged(event: SensorEvent?) {

                //This on sensor changed is called. sensor changed means the value that hardware is measuring when it get changes. this function is called
                super.onSensorChanged(event)
                Log.d("SENSOR", """
                    values :
                    ax: ${event?.values?.get(0)}
                    ay: ${event?.values?.get(1)}
                    az: ${event?.values?.get(2)}
                """.trimIndent())
                //since the accelerometer measures the acceleration of a particular position therefore it gives the coordinates in x dir, y dir and z dir. therefore event will always be of type array as it can get us multiple values

                val ax = event?.values?.get(0) ?:0f
                val ay = event?.values?.get(1) ?:0f
                val az = event?.values?.get(2) ?:0f


                //Since the android has provided us with 255 colours , therefore we will convert t

                frameLayout.setBackgroundColor(
                    //Here it will mix the red,green,blue and get that colour on screen
                    Color.rgb(
                        (((ax+12)/25)*255).toInt(),
                        (((ay+12)/25)*255).toInt(),
                        (((az+12)/25)*255).toInt()
                    )
                )
            }
        }

        sm.registerListener(
            sensorListener,
            accelerationSensor,
            1000 * 60
        )
    }
}
