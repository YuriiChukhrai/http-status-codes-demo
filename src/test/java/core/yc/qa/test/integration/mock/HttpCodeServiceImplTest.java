package core.yc.qa.test.integration.mock;

import core.yc.qa.http.codes.entity.HttpCode;
import core.yc.qa.http.codes.exception.model.EntityExistException;
import core.yc.qa.http.codes.exception.model.EntityNotFoundException;
import core.yc.qa.http.codes.exception.model.IllegalRequestException;
import core.yc.qa.http.codes.repositories.HttpCodeRepository;
import core.yc.qa.http.codes.services.HttpCodeServiceImpl;

import core.yc.qa.test.TestGroups;
import io.qameta.allure.*;
import org.mockito.*;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

/**
 * @author limit (Yurii Chukhrai)
 *
 * Service layer unit tests with mocked repository layer (Database) only.
 *
 * @MockBean issue: https://github.com/spring-projects/spring-boot/issues/7689
 *
 */
@Listeners(MockitoTestNGListener.class)
public class HttpCodeServiceImplTest {

    @Mock
    HttpCodeRepository httpCodeRepository;

    @InjectMocks
    private HttpCodeServiceImpl httpCodeServiceImpl;


    @Description("Get HTTP codes records size")
    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-001"), @Story("Stories: CIR-002")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("001"), @TmsLink("002")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(enabled = true, groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void getHttpCodesSizeTest() {

        when(httpCodeRepository.count()).thenReturn(77L);

        Assert.assertNotNull(httpCodeServiceImpl.getHttpCodesSize());
        Mockito.verify(httpCodeRepository).count();
    }

    @Description("Find HTTP code by negative code")
    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-003"), @Issue("GTA-004")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic03"), @Epic("Epic04")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong code \\[.*\\]")
    public void findHttpCodeByNegativeCodeTest() {
        httpCodeServiceImpl.findHttpCodeByCode(-999);
    }

    @Description("Find HTTP code by wrong code")
    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-005"), @Issue("GTA-006")})
    @Stories({@Story("Stories: CIR-005"), @Story("Stories: CIR-006")})
    @Epics({@Epic("Epic05"), @Epic("Epic06")})
    @TmsLinks({@TmsLink("005"), @TmsLink("006")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityNotFoundException.class, expectedExceptionsMessageRegExp = "HttpCode was not found for parameters \\{code=\\d+\\}")
    public void findHttpCodeByWrongCodeTest() {
        final int code = 999;
        when(httpCodeRepository.findHttpCodeByCode(code)).thenReturn(Optional.empty());

        httpCodeServiceImpl.findHttpCodeByCode(code);
    }

    @Description("Find HTTP code by code")
    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-007"), @Issue("GTA-008")})
    @Stories({@Story("Stories: CIR-007"), @Story("Stories: CIR-008")})
    @Epics({@Epic("Epic07"), @Epic("Epic08")})
    @TmsLinks({@TmsLink("007"), @TmsLink("008")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void findHttpCodeByCodeTest() {
        final int code = 555;
        final HttpCode httpCode = new HttpCode().setCode(code).setCategory("5** Server Error").setReason_phrase("Bad Connection").setDefinition("We can't guarantee the network connection.");

        when(httpCodeRepository.findHttpCodeByCode(code)).thenReturn(Optional.of(httpCode));

        httpCodeServiceImpl.findHttpCodeByCode(code);
        Mockito.verify(httpCodeRepository).findHttpCodeByCode(Mockito.anyInt());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-009"), @Issue("GTA-010")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic09"), @Epic("Epic10")})
    @TmsLinks({@TmsLink("009"), @TmsLink("010")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong ID \\[.*\\]")
    public void findHttpCodeByNegativeIdTest() {
        httpCodeServiceImpl.findHttpCodeById(-999L);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityNotFoundException.class, expectedExceptionsMessageRegExp = "HttpCode was not found for parameters \\{id=\\d+\\}")
    public void findHttpCodeByWrongIdTest() {
        final long id = 999L;
        when(httpCodeRepository.findById(id)).thenReturn(Optional.empty());

        httpCodeServiceImpl.findHttpCodeById(id);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void findHttpCodeByIdTest() {
        final long id = 1;
        final HttpCode httpCode = new HttpCode().setCode(555).setCategory("5** Server Error").setReason_phrase("Bad Connection").setDefinition("We can't guarantee the network connection.");

        when(httpCodeRepository.findById(id)).thenReturn(Optional.of(httpCode));

        httpCodeServiceImpl.findHttpCodeById(id);
        Mockito.verify(httpCodeRepository).findById(Mockito.anyLong());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong category \\[\\w+\\]")
    public void findHttpCodesByCategoryNullTest() {
        httpCodeServiceImpl.findHttpCodesByCategory(null);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong category \\[.*\\]")
    public void findHttpCodesByCategoryEmptyTest() {
        httpCodeServiceImpl.findHttpCodesByCategory("");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong category \\[.*\\]")
    public void findHttpCodesByCategoryHugeInputTest() {
        httpCodeServiceImpl.findHttpCodesByCategory("1** Informational; 2** Success; 3** Redirection");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void findHttpCodesByCategorTest() {
        final String input = "1** Informational";
        final List<HttpCode> outputExpectedList = new ArrayList<>( Arrays.asList(
                new HttpCode()
                        .setCode(100)
                        .setCategory("1** Informational")
                        .setReason_phrase("Continue")
                        .setDefinition("This interim response indicates that the client should continue the request or ignore the response if the request is already finished."),

                new HttpCode()
                        .setCode(102)
                        .setCategory("1** Informational")
                        .setReason_phrase("Processing")
                        .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.")
        )
        );

        final Stream<HttpCode> outputExpectedStream = outputExpectedList.stream();

        when(httpCodeRepository.findHttpCodesByCategory(input)).thenReturn(outputExpectedStream);

        final List<HttpCode> outputActualList = httpCodeServiceImpl.findHttpCodesByCategory("1** Informational");
        Mockito.verify(httpCodeRepository).findHttpCodesByCategory(Mockito.anyString());

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(outputActualList);
        sa.assertFalse(outputActualList.isEmpty());
        sa.assertEquals(outputActualList, outputExpectedList);

        sa.assertAll("Verification input [Category]");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong reason phrase \\[\\w+\\]")
    public void findHttpCodeByReasonPhraseNullTest() {
        httpCodeServiceImpl.findHttpCodeByReasonPhrase(null);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong reason phrase \\[.*\\]")
    public void findHttpCodeByReasonPhraseEmptyTest() {
        httpCodeServiceImpl.findHttpCodeByReasonPhrase("");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong reason phrase \\[.*\\]")
    public void findHttpCodeByReasonPhraseHugeInputTest() {
        httpCodeServiceImpl.findHttpCodeByReasonPhrase("Continue; Switching; Processing; Early Hints; Non-authoritative Information");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityNotFoundException.class)
    public void findHttpCodeByReasonPhraseNotFoundTest() {
        final String reasonPhrase = "Continue";
        final Optional<HttpCode> response = Optional.empty();

        when(httpCodeRepository.findHttpCodeByReasonPhrase(reasonPhrase)).thenReturn(response);

        httpCodeServiceImpl.findHttpCodeByReasonPhrase(reasonPhrase);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void findHttpCodeByReasonPhraseFoundTest() {
        final String reasonPhrase = "Multiple Choices";
        final Optional<HttpCode> expectedResponse = Optional.of(new HttpCode()
                .setCode(300)
                .setCategory("3** Redirection")
                .setReason_phrase(reasonPhrase)
                .setDefinition("The request has more than one possible response."));

        when(httpCodeRepository.findHttpCodeByReasonPhrase(reasonPhrase)).thenReturn(expectedResponse);

        final HttpCode actualResponse = httpCodeServiceImpl.findHttpCodeByReasonPhrase(reasonPhrase);

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(actualResponse);
        sa.assertEquals(actualResponse, expectedResponse.get());

        sa.assertAll("Verification input [Reason Phrase]");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void getAllHttpCodesTest() {
        final List<HttpCode> outputExpectedList = new ArrayList<>( Arrays.asList(
                new HttpCode()
                        .setCode(100)
                        .setCategory("1** Informational")
                        .setReason_phrase("Continue")
                        .setDefinition("This interim response indicates that the client should continue the request or ignore the response if the request is already finished."),

                new HttpCode()
                        .setCode(102)
                        .setCategory("1** Informational")
                        .setReason_phrase("Processing")
                        .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.")
        )
        );

        when(httpCodeRepository.findAll()).thenReturn(outputExpectedList);

        final List<HttpCode> outputActualList = httpCodeServiceImpl.getAllHttpCodes();
        Mockito.verify(httpCodeRepository).findAll();

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(outputActualList);
        sa.assertFalse(outputActualList.isEmpty());
        sa.assertEquals(outputActualList, outputExpectedList);

        sa.assertAll("Verification output [Get all HttpCode's]");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong ID \\[\\D?\\d+\\]")
    public void deleteHttpCodeNegaativeIdTest() {
        httpCodeServiceImpl.delete(-10);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class, expectedExceptionsMessageRegExp = "Wrong ID \\[\\D?\\d+\\]")
    public void deleteHttpCodeGoldenIdRangeTest() {
        httpCodeServiceImpl.delete( 66);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityNotFoundException.class)
    public void deleteHttpCodeNotFoundTest() {
        final long httpCodeId = 77L;

        when(httpCodeRepository.findById(httpCodeId)).thenReturn(Optional.empty());
        httpCodeServiceImpl.delete(httpCodeId);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void deleteHttpCodeTest() {
        final long httpCodeId = 77L;
        final HttpCode httpCode = new HttpCode()
                .setCode(102)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        when(httpCodeRepository.findById(httpCodeId)).thenReturn(Optional.of(httpCode));
        doNothing().when(httpCodeRepository).deleteById(isA(Long.class));

        httpCodeServiceImpl.delete(httpCodeId);

        Mockito.verify(httpCodeRepository).findById(Mockito.anyLong());
        Mockito.verify(httpCodeRepository).deleteById(Mockito.anyLong());
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityExistException.class)
    public void saveNullHttpCodeTest() {
        httpCodeServiceImpl.save(null);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityExistException.class)
    public void saveWrongHttpCodeTest() {
        final HttpCode httpCode = new HttpCode()
                .setCode(-102)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        httpCodeServiceImpl.save(httpCode);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = EntityExistException.class)
    public void saveExistHttpCodeTest() {
        final HttpCode httpCode = new HttpCode()
                .setCode(102)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        when(httpCodeRepository.findHttpCodeByCode(httpCode.getCode())).thenReturn(Optional.of(httpCode));
        httpCodeServiceImpl.save(httpCode);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void saveNewHttpCodeTest() {
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        when(httpCodeRepository.findHttpCodeByCode(httpCodeExpected.getCode())).thenReturn(Optional.empty());
        when(httpCodeRepository.save(httpCodeExpected)).thenReturn(httpCodeExpected);

        final HttpCode httpCodeActual = httpCodeServiceImpl.save(httpCodeExpected);

        Mockito.verify(httpCodeRepository).findHttpCodeByCode(Mockito.anyInt());
        Mockito.verify(httpCodeRepository).save(Mockito.any(HttpCode.class));

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(httpCodeActual);
        sa.assertEquals(httpCodeActual, httpCodeExpected);

        sa.assertAll("Verification save [HttpCode]");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class)
    public void putNullHttpCodeTest() {
        httpCodeServiceImpl.put(null, -10);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class)
    public void putHttpCodeNegativeCodeTest() {
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(-111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        httpCodeServiceImpl.put(httpCodeExpected, -10);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class)
    public void putHttpCodeNegativeIdTest() {
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        httpCodeServiceImpl.put(httpCodeExpected, -10);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK}, expectedExceptions = IllegalRequestException.class)
    public void putHttpCodeGoldIdRangeTest() {
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        httpCodeServiceImpl.put(httpCodeExpected, 66);
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void putHttpCodeSaveNewTest() {
        final long expectedId = 77;
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        when(httpCodeRepository.findById(expectedId)).thenReturn(Optional.empty());
        when(httpCodeRepository.save(httpCodeExpected)).thenReturn(httpCodeExpected);

        final HttpCode httpCodeActual = httpCodeServiceImpl.put(httpCodeExpected, expectedId);

        Mockito.verify(httpCodeRepository).findById(Mockito.anyLong());
        Mockito.verify(httpCodeRepository).save(Mockito.any(HttpCode.class));

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(httpCodeActual);
        sa.assertEquals(httpCodeActual, httpCodeExpected);

        sa.assertAll("Verification save [HttpCode]");
    }

    @Features({@Feature(TestGroups.INTEGRATION), @Feature(TestGroups.MOCK)})
    @Issues({@Issue("GA-001"), @Issue("GTA-002")})
    @Stories({@Story("Stories: CIR-003"), @Story("Stories: CIR-004")})
    @Epics({@Epic("Epic01"), @Epic("Epic02")})
    @TmsLinks({@TmsLink("003"), @TmsLink("004")})
    @Links({@Link(url = "https://github.com/YuriiChukhrai/http-status-codes-demo/", name = "GitHub repository"), @Link(url = "https://www.linkedin.com/in/yurii-c-b55aa6174/", name = "LinkedIn")})
    @Severity(SeverityLevel.NORMAL)
    @Owner("Yurii Chukhrai")
    @Test(groups = {TestGroups.INTEGRATION, TestGroups.MOCK})
    public void putHttpCodeUpdateTest() {
        final long expectedId = 77;
        final HttpCode httpCodeExpected = new HttpCode()
                .setCode(111)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        final HttpCode httpCodeExpectedUpdates = new HttpCode()
                .setCode(0000)
                .setCategory("1** Informational")
                .setReason_phrase("Processing")
                .setDefinition("This code indicates that the server has received and is processing the request, but no response is available yet.");

        when(httpCodeRepository.findById(expectedId)).thenReturn(Optional.of(httpCodeExpectedUpdates));

        final HttpCode httpCodeActual = httpCodeServiceImpl.put(httpCodeExpected, expectedId);

        Mockito.verify(httpCodeRepository).findById(Mockito.anyLong());
        Mockito.verify(httpCodeRepository, never()).save(Mockito.any(HttpCode.class));

        final SoftAssert sa = new SoftAssert();
        sa.assertNotNull(httpCodeActual);
        sa.assertEquals(httpCodeActual, httpCodeExpected);

        sa.assertAll("Verification save [HttpCode]");
    }
}
