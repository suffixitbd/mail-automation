import driver.WebDriverClient;
import networking.APIInterface;
import networking.RetrofitClient;
import okhttp3.ResponseBody;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.time.Duration;

public class Main {

    public static WebDriver driver;

    public static void main(String[] args) {
        driver = WebDriverClient.getInstance().getFirefoxDriver();

        APIInterface apiInterface = RetrofitClient.getRetrofit().create(APIInterface.class);

        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(3))
                .ignoring(NoSuchElementException.class);

        try {
            driver.get("https://emailfake.com/");
            readTableData(wait, tableData -> {
                System.out.println(tableData.getText());
                Call<ResponseBody> call = apiInterface.postOtp(tableData.getText(), tableData.getText());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            System.out.println("success : " + response.body().toString());
                            driver.quit();
                        } else {
                            System.out.println(response.errorBody().charStream());
                            driver.quit();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println(t.getMessage());
                        driver.quit();
                    }
                });
            });
        } finally {
            //driver.quit();
        }
    }

    public static void readTableData(Wait<WebDriver> wait, TableInterface tableInterface) {
        try {
            WebElement table = wait.until(webDriver -> webDriver.findElement(
                    By.xpath("/html/body/div[4]/div/div/div/div[2]/div[2]/div[4]/div[3]/div")));
            try {
                tableInterface.onTablePresent(table);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e.getLocalizedMessage().contains("tried for 5 second(s) with 3000 milliseconds interval")) {
                driver.navigate().refresh();
                readTableData(wait, tableInterface);
            }
        }
    }
}
