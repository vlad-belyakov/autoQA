Показательный репозиторий с простым юнит-тестом калькулятора, а так же функциональные тесты таких сервисов как Youtube, Rutube и Wildberries. Так же имеются баг репорты и тест-кейсы по сервисам Youtube и Wildberries в сервисе Qase
Были использованы такие фреймворки как Selenium и JUnit

## Тестирование

Для тестирования функциональности калькулятора используется JUnit 5.

### Настройка тестов
```java
@BeforeAll
public static void setUp() {
    calculator = new Calculator();
    LOG.info("Тест калькулятора сконфигурирован");
}

@BeforeEach
public void logInfo() {
    LOG.info(String.valueOf(counter));
    counter++;
}
```
- `@BeforeAll` создаёт объект `Calculator` перед запуском всех тестов.
- `@BeforeEach` логирует счётчик перед каждым тестом.

### Тест деления на ноль
```java
@Test
public void divideByZeroTest() {
    Assertions.assertThrows(ArithmeticException.class, () -> {
        calculator.div(1,0);
    });
}
```
Проверяет, выбрасывает ли метод `div()` исключение `ArithmeticException` при делении на 0.

### Улучшенный тест деления на ноль
```java
@ParameterizedTest
@ValueSource(strings = "невозможно поделить на ноль")
public void upgDivideByZeroTest(String text) {
    Assertions.assertEquals(calculator.uprgradedDiv(2, 0), text);
}
```
Проверяет, что улучшенный метод `uprgradedDiv()` возвращает `"невозможно поделить на ноль"` вместо исключения.

### Тест сложения с `null`
```java
@ParameterizedTest
@NullSource
public void nullSumTest(Integer argument) {
    Assertions.assertNull(argument);
}
```
Проверяет, что аргумент `null` корректно обрабатывается тестируемым методом.

## Selenium Конфигурация

Для работы с Selenium используется WebDriver и ChromeDriver.

### Настройка Selenium
```java
public class SeleniumConfig {
    static WebDriver driver;
    static Config youtubeConfig;
    static Config rutubeConfig;
    static Config wildberriesConfig;

    @BeforeAll
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterAll
    public static void quit() {
        driver.quit();
    }
}
```
- `setUp()` настраивает ChromeDriver перед началом тестов.
- `quit()` завершает работу WebDriver после выполнения всех тестов.

# Wildberries Test

## Описание
Этот проект содержит тест для поиска товаров на Wildberries с использованием Selenium WebDriver и JUnit.

## Тестируемый класс
```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WildberriesTest extends SeleniumConfig{

    private WebDriverWait wait;
    @FindBy(xpath = "//input[@id='searchInput']")
    private WebElement searchQuery;

    private List<WebElement> goodsList;

    public WildberriesTest(){
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wildberriesConfig = ConfigFactory.parseResources("ruservices.conf");
        PageFactory.initElements(driver, this);
    }

    @Order(1)
    @Test
    public void searchAndCheckGoodsTest() {
        String expectedValue = "Миндальная";
        List<String> wrongValues = new ArrayList<>();
        driver.get(wildberriesConfig.getString("wildberries.main_page_url"));
        searchQuery.sendKeys(wildberriesConfig.getString("wildberries.search_text"));
        searchQuery.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class = 'product-card__wrapper']/a")));
        goodsList = driver.findElements(
                By.xpath("//div[@class = 'product-card__wrapper']/a"));
        for (WebElement element: goodsList){
            String goodsName = element.getAttribute("aria-label");
            if (!(goodsName.contains(expectedValue) || goodsName.contains(expectedValue.toLowerCase()))) {
                wrongValues.add(goodsName);
            }
        }

        Assertions.assertNull(wrongValues.get(0));
    }
}
```

## Ожидаемый результат
Тест проверяет, что все найденные товары содержат в названии слово "Миндальная". В случае ошибки выводится список товаров, не соответствующих критерию.


