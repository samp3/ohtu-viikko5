
import ohtu.verkkokauppa.Kauppa;
import ohtu.verkkokauppa.Pankki;
import ohtu.verkkokauppa.Tuote;
import ohtu.verkkokauppa.Varasto;
import ohtu.verkkokauppa.Viitegeneraattori;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author sampe
 */
public class Tests {

    public Tests() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void ostoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaan() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, ettÃ¤ pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(anyString(), anyInt(), anyString(), anyString(), anyInt());
        // toistaiseksi ei vÃ¤litetty kutsussa kÃ¤ytetyistÃ¤ parametreista
    }

    @Test
    public void test1() {
        //aloitetaan asiointi, koriin lisÃ¤tÃ¤Ã¤n tuote, jota varastossa on ja suoritetaan ostos, eli kutsutaan metodia kaupan tilimaksu(). varmistettava ettÃ¤ kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
        //tÃ¤mÃ¤ siis on muuten copypaste esimerkistÃ¤, mutta verify:ssÃ¤ on tarkastettava ettÃ¤ parametreilla on oikeat arvot
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, ettÃ¤ pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));
        //String nimi, int viitenumero, String tililta, String tilille, int summa
    }

    //aloitetaan asiointi, koriin lisÃ¤tÃ¤Ã¤n kaksi eri tuotetta, joita varastossa on ja suoritetaan ostos. 
    //varmistettava ettÃ¤ kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void test2() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 2));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa       
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(7));

    }

//    aloitetaan asiointi, koriin lisätään kaksi samaa tuotetta jota on varastossa tarpeeksi ja suoritetaan ostos. 
//    varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla
    @Test
    public void test3() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa       
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(10));

    }
    //aloitetaan asiointi, koriin lisätään tuote jota on varastossa tarpeeksi ja tuote joka on loppu ja suoritetaan ostos. 
    //varmistettava että kutsutaan pankin metodia tilisiirto oikealla asiakkaalla, tilinumerolla ja summalla

    @Test
    public void test4() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 2));
        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa       
        k.lisaaKoriin(2);
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));
    }

    //varmistettava, että metodin aloitaAsiointi kutsuminen nollaa edellisen ostoksen tiedot 
    //(eli edellisen ostoksen hinta ei näy uuden ostoksen hinnassa), katso tarvittaessa apua projektin MockitoDemo testeistä!
    @Test
    public void test5() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 2));

        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("seppo", "1234");

        verify(pankki).tilisiirto(eq("seppo"), eq(42), eq("1234"), eq("33333-44455"), eq(2));
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa       
        k.tilimaksu("pekka", "12345");
        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), eq("33333-44455"), eq(5));
    }
    //varmistettava, että kauppa pyytää uuden viitenumeron jokaiselle maksutapahtumalle, 
    //katso tarvittaessa apua projektin MockitoDemo testeistä!
    @Test
    public void test6() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);

        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2, "piima", 2));

        Kauppa k = new Kauppa(varasto, pankki, viite);

        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("seppo", "1234");

        verify(viite, times(1)).uusi();
         k.lisaaKoriin(2);
        k.tilimaksu("seppo", "1234");
        verify(viite, times(2)).uusi();
        
    }
    @Test
    public void test7() {
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // mÃ¤Ã¤ritellÃ¤Ã¤n ettÃ¤ tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdÃ¤Ã¤n ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);    // ostetaan tuotetta numero 1 eli maitoa
        k.poistaKorista(1);
        
        
        
 
    }
}
