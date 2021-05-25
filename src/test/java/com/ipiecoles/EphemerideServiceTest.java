package com.ipiecoles;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class EphemerideServiceTest {

    @Spy
    @InjectMocks
    private EphemerideService ephemerideService;


    @Test
    public void test_getResponse_sucess() {
        //Given
        //When
        Ephemeride ephemeride = this.ephemerideService.getResponse(LocalDate.of(2021,01,01));
        //Then
        Assert.assertEquals("vendredi 01 janvier 2021", ephemeride.getDateJour());
        Assert.assertEquals(" Jour de l'An", ephemeride.getFeteDuJour());
        Assert.assertEquals("1", ephemeride.getJourAnnee().toString());
        Assert.assertEquals("364", ephemeride.getJoursRestants().toString());
        Assert.assertEquals("1", ephemeride.getNumSemaine().toString());
    }
}
