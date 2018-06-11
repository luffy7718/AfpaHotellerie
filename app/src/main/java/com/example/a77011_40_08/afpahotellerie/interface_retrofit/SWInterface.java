package com.example.a77011_40_08.afpahotellerie.interface_retrofit;




import com.example.a77011_40_08.afpahotellerie.models.Push;

import java.lang.annotation.Annotation;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SWInterface {

    /******************************************
     * CALL
    ******************************************/
    @POST("/afpa_hotellerie/getDBVersion.php")
    Call<Integer> getDBVersion(
            @Header("Authorization") String authorization
    );

    @FormUrlEncoded
    @POST("/afpa_hotellerie/forceLogin.php")
    //@POST("/afpa_hotellerie/login.php")
    Call<Push> login(
            @Header("Authorization") String authorization,
            @Field("login") String login,
            @Field("password") String password,
            @Field("idDevice") int idDevice);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/logout.php")
    Call<Push> logout(
            @Header("Authorization") String authorization,
            @Field("idStaff") int idStaff);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/sendMessage.php")
    Call<Push> sendMessage(
            @Header("Authorization") String authorization,
            @Field("to") int to,
            @Field("from") int from,
            @Field("type") String type,
            @Field("body") String body);

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
    @POST("/afpa_hotellerie/getMessagesChat.php")
    Call<Push> getMessagesChat(
            @Header("Authorization") String authorization,
            @Field("idDevice") int idDevice,
            @Field("idUser") int idUser);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/getUsers.php")
    Call<Push> getUsers(
            @Header("Authorization") String authorization,
            @Field("idDevice") int idDevice);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/addRoomsHistory.php")
    Call<Push> addRoomsHistory(
            @Header("Authorization") String authorization,
            @Field("idRoom") int idRoom,
            @Field("idStaff") int idStaff,
            @Field("date") String date,
            @Field("idRoomStatus") int idRoomStatus);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/addDevice.php")
    Call<Push> addDevice(
            @Header("Authorization") String authorization,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/setDevice.php")
    Call<Push> setDevice(
            @Header("Authorization") String authorization,
            @Field("idDevice") int idDevice,
            @Field("token") String token);


    @POST("/afpa_hotellerie/getStaff.php")
    Call<Push> getStaff(
            @Header("Authorization") String authorization);

    @POST("/afpa_hotellerie/getRooms.php")
    Call<Push> getRooms(
            @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/getSubordinates.php")
    Call<Push> getSubordinates(
            @Header("Authorization") String authorization,
            @Field("idStaff") int idStaff);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/getRoomsToClean.php")
    Call<Push> getRoomsToClean(
            @Header("Authorization") String authorization,
            @Field("idStaff") int idStaff);

    @POST("/afpa_hotellerie/getUnassignedRooms.php")
    Call<Push> getUnassignedRooms(
            @Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/removeAssignment.php")
    Call<Push> removeAssignment(
            @Header("Authorization") String authorization,
            @Field("idRoom") int idRoom);

    @FormUrlEncoded
    @POST("/afpa_hotellerie/addAssignment.php")
    Call<Push> addAssignment(
            @Header("Authorization") String authorization,
            @Field("idRoom") int idRoom,
            @Field("idStaff") int idStaff);

    /******************************************
     * OBSERVABLE
     ******************************************/

    @POST("/afpa_hotellerie/getJobs.php")
    Observable<Push> getJobs(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getRoomsStatus.php")
    Observable<Push> getRoomsStatus(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getFloors.php")
    Observable<Push> getFloors(
            @Header("Authorization") String authorization
    );
    @POST("/afpa_hotellerie/getRoomsTypes.php")
    Observable<Push> getRoomsTypes(
            @Header("Authorization") String authorization
    );


}
