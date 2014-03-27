package com.example.giambi.test;

import com.example.giambi.GiambiHttpClient;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionTest extends TestCase {

    @Mock
    private GiambiHttpClient giambiHttpClient;

    @Before
    protected void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


}
