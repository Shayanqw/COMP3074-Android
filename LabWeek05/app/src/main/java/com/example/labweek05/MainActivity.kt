package com.example.labweek05

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LightApp() }
    }
}

@Composable
fun LightApp() {
    val context = LocalContext.current
    val sensorManager = remember {
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
    val lightSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) }

    // UI state
    // force demo even when there is no sensor
    var lux by remember { mutableFloatStateOf(0f) }
    var useDemo by remember { mutableStateOf(lightSensor == null) }
    val label = remember(lux) { labelForLux(lux) }

    if (!useDemo && lightSensor != null) {
        DisposableEffect(lightSensor) {
            val listener = object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent) {
                    lux = event.values.firstOrNull() ?: lux
                }
                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
            }
            sensorManager.registerListener(
                listener,
                lightSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
            onDispose { sensorManager.unregisterListener(listener) }
        }
    }

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Ambient Light Monitor",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold
                )

                // Sensor availability + Demo toggle
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (lightSensor == null)
                            "No light sensor found (demo mode only)"
                        else
                            "Light sensor: available"
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Demo", modifier = Modifier.padding(end = 8.dp))
                        Switch(
                            checked = useDemo,
                            onCheckedChange = { checked ->
                                useDemo = checked
                                if (checked) lux = 0f
                            }
                        )
                    }
                }

                // Live reading (sensor or demo)
                ElevatedCard {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Current lux: ${lux.toInt()}")
                        Text(
                            text = label,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                // in this part i added Demo slider to shown only in demo mode
                if (useDemo) {
                    LightDemoFallback(
                        value = lux,
                        onValueChange = { lux = it }
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    "Tip: Cover the top of the phone (near the earpiece) to reduce lux, " +
                            "or shine a lamp to increase it. On emulator, open Extended Controls → Sensors → Light.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

private fun labelForLux(lux: Float): String = when {
    lux == 0f -> "Pitch black"
    lux in 1f..10f -> "Dark"
    lux in 11f..50f -> "Grey"
    lux in 51f..5000f -> "Normal"
    lux in 5001f..25000f -> "Incredibly bright"
    else -> "This light will blind you"
}

@Composable
fun LightDemoFallback(
    value: Float,
    onValueChange: (Float) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text("Fake lux: ${value.toInt()}")
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..50000f
        )
        Spacer(Modifier.height(12.dp))
        Text(labelForLux(value), style = MaterialTheme.typography.headlineSmall)
    }
}
