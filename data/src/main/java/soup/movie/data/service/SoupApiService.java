package soup.movie.data.service;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import soup.movie.data.model.CodeResponse;
import soup.movie.data.model.NowMovieResponse;
import soup.movie.data.model.PlanMovieResponse;
import soup.movie.data.model.TimeTableResponse;

public interface SoupApiService {

    String API_BASE_URL = "https://soup-movie.firebaseio.com/";

    // 현재상영작
    @GET("v1/cgv/now/asTicketRate/all.json")
    Single<NowMovieResponse> getNowList();

    // 개봉예정작
    @GET("v1/cgv/plan/asOpenDate.json")
    Single<PlanMovieResponse> getPlanList();

    // 공통코드
    @GET("v1/cgv/code.json")
    Single<CodeResponse> getCodeList();

    // 상영시간표
    @GET("v1/cgv/timetable/asMovie/{tc}/{mc}.json")
    Single<TimeTableResponse> getTimeTableList(@Path("tc") String theater,
                                               @Path("mc") String movie);
}
