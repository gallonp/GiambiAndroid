package com.example.giambi.test;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.giambi.GiambiHttpClient;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {
	
@Mock
private GiambiHttpClient giambiHttpClient;
	
@Before
protected void setUp() throws Exception {
  MockitoAnnotations.initMocks(this);
}

	
	
}
