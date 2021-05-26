package com.ipiecoles;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyString;


import java.io.IOException;
import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class EphemerideServiceTest {

    @Spy
    @InjectMocks
    private EphemerideService ephemerideService;

    @Mock
    private WebUtils webUtils;

    private String mockResponseRequete(){
        return ("{\"january\":[[\"Jour de l'An\", \"\"],[\"Basile\", \"Saint\"],[\"Geneviève\",\"Sainte\"]]}");
    }

    @Test
    public void test_getResponseWithMock_sucess() throws IOException {
        //Given
        when(this.webUtils.getPageContents(anyString())).thenReturn(this.mockResponseRequete());
        //When
        Ephemeride ephemeride = this.ephemerideService.getResponse(LocalDate.of(2021,01,01));
        //Then
        Assert.assertEquals("vendredi 01 janvier 2021", ephemeride.getDateJour());
        Assert.assertEquals(" Jour de l'An", ephemeride.getFeteDuJour());
        Assert.assertEquals("1", ephemeride.getJourAnnee().toString());
        Assert.assertEquals("364", ephemeride.getJoursRestants().toString());
        Assert.assertEquals("1", ephemeride.getNumSemaine().toString());
    }

    @Test
    public void test_getResponse_sucess() throws IOException{
        //Given
        when(this.webUtils.getPageContents(anyString())).thenCallRealMethod();
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
