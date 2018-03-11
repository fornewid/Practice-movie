package soup.movie.data;

import com.google.gson.annotations.SerializedName;

public class WeeklyBoxOfficeResponse {

    @SerializedName("boxOfficeResult")
    private WeeklyBoxOfficeResult result;

    public WeeklyBoxOfficeResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "WeeklyBoxOfficeResponse{" +
                "result=" + result +
                '}';
    }
}
