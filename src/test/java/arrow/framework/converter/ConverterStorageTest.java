/**
 *
 */
package arrow.framework.converter;

import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.mockito.Mockito;

import pl.itcrowd.arquillian.mock_contexts.MockFacesContextProducer;
import arrow.mems.faces.converter.ConverterStorage;


@UsingDataSet({"base_data.xls"})
public class ConverterStorageTest {
  // Here is how to create Mock of FacesContext
  @MockFacesContextProducer
  public FacesContext createFacesContext() {
    final FacesContext context = Mockito.mock(FacesContext.class);

    return context;
  }

  @Inject
  ConverterStorage convererStorage;

  /**
   * Test method for
   * {@link arrow.businesstraceability.converter.ConverterStorage#getAddressPointMstConverter()}.
   */
  // @Test
  // @FacesContextRequired
  // public void testGetAddressPointMstConverter() throws Exception {
  // // how to test a converter?
  // final Converter test = this.convererStorage.getAddressConverter();
  // final Address addr = (Address) test.getAsObject(FacesContext.getCurrentInstance(), null, "01");
  //
  // AssertJUnit.assertNotNull(addr);
  // AssertJUnit.assertEquals(addr.getAdp_name_DIRECT(), "北海道");
  //
  // final String addressCode = test.getAsString(FacesContext.getCurrentInstance(), null, addr);
  // AssertJUnit.assertEquals(addressCode, "01");
  //
  // }
  //
  // @Test(expectedExceptions = ConverterException.class)
  // @FacesContextRequired
  // public void testGetAddressPointMstConverterFailed() throws Exception {
  // final Converter test = this.convererStorage.getAddressPointMstConverter();
  // final Addresspoint_mst addrFailed = (Addresspoint_mst)
  // test.getAsObject(FacesContext.getCurrentInstance(), null, "00");
  // AssertJUnit.assertNull(addrFailed);
  //
  // final String addrCodeFailed = test.getAsString(FacesContext.getCurrentInstance(), null, null);
  // Assert.assertNull(addrCodeFailed);
  // }

  // @Test
  // @FacesContextRequired
  // public void testGetIndustryBigInfoConverter() throws Exception {
  // // how to test a converter?
  // Converter test = this.convererStorage.getIndustryBigInfoConverter();
  // Industry_big_mst industryBig = (Industry_big_mst)
  // test.getAsObject(FacesContext.getCurrentInstance(), null, "101");
  //
  // AssertJUnit.assertNotNull(industryBig);
  // AssertJUnit.assertEquals("建設", industryBig.getBig_name_DIRECT());
  //
  // String bigCode = test.getAsString(FacesContext.getCurrentInstance(), null, industryBig);
  // AssertJUnit.assertEquals(bigCode, "101");
  //
  // }
  //
  // @Test(expectedExceptions = ConverterException.class)
  // @FacesContextRequired
  // public void testGetIndustryBigInfoConverterFailed() throws Exception {
  // Converter test = this.convererStorage.getIndustryBigInfoConverter();
  // Industry_big_mst bigFailed = (Industry_big_mst)
  // test.getAsObject(FacesContext.getCurrentInstance(), null, "00");
  // AssertJUnit.assertNull(bigFailed);
  //
  // String addrCodeFailed = test.getAsString(FacesContext.getCurrentInstance(), null, null);
  // Assert.assertNull(addrCodeFailed);
  // }
}
