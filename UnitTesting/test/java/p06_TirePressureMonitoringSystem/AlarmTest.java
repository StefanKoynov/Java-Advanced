package p06_TirePressureMonitoringSystem;

import org.junit.Assert.*;
import org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AlarmTest {

    @Test
    public void testAlarmWithNormalValue() {

        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(19.3);
        Alarm alarm = new Alarm(sensor);

        alarm.check();
        assertFalse(alarm.getAlarmOn());

    }

    @Test
    public void testAlarmWithLowerValue() {

        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(15.3);
        Alarm alarm = new Alarm(sensor);

        alarm.check();
        assertTrue(alarm.getAlarmOn());

    }

    @Test
    public void testAlarmWithHigherValue() {
        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(24.1);
        Alarm alarm = new Alarm(sensor);

        alarm.check();
        assertTrue(alarm.getAlarmOn());

    }


}
