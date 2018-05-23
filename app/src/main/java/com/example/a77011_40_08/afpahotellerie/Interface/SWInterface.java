package com.example.a77011_40_08.afpahotellerie.Interface;



import com.example.a77011_40_08.afpahotellerie.Models.Floor;
import com.example.a77011_40_08.afpahotellerie.Models.Job;
import com.example.a77011_40_08.afpahotellerie.Models.Push;
import com.example.a77011_40_08.afpahotellerie.Models.RoomStatut;
import com.example.a77011_40_08.afpahotellerie.Models.User;
import com.example.a77011_40_08.afpahotellerie.Models.Users;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SWInterface {

    @POST("/afpa_hotellerie/getDBVersion.php")
    Call<Integer> getDBVersion(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("/afpa_hotellerie/login.php")
    Call<Push> login(
            @Header("Authorization") String authorization,
            @Field("login") String login,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("/afpa_hotellerie/addUser.php")
    Call<User> account(
            @Field("name") String name,
            @Field("firstname") String firstname,
               @Field("login") String login,
             @Field("password") String password);



    @POST("/afpa_hotellerie/getJobs.php")
    Observable<Push> jobs(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getRoomsStatus.php")
    Observable<Push>RoomsStatus(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getFloors.php")
    Observable<Push>Floors(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getRoomsTypes.php")
    Observable<Push>RoomsTypes(
            @Header("Authorization") String authorization
    );


    @FormUrlEncoded
    @POST("/afpa_hotellerie/getAssignedRooms.php")
    Call<Push> getAssignedRooms(
            @Header("Authorization") String authorization,
            @Field("idStaff") int idStaff);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/getFurnituresTroublesByIdRoom.php")
    Call<Push> getFurnituresTroublesByIdRoom(
            @Header("Authorization") String authorization,
            @Field("idRoom") int idRoom);
    @FormUrlEncoded
    @POST("/afpa_hotellerie/addRoomsHistory.php")
    Call<Push> addRoomsHistory(
            @Header("Authorization") String authorization,
            @Field("idRoom") int idRoom,
            @Field("idStaff") int idStaff,
            @Field("date") String date,
            @Field("idRoomStatus") int idRoomStatus);

}
