package bugfix.itsolutions.jsfoodi.models;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RestaurantDetail.kt */
/* loaded from: classes4.dex */
public final class RestaurantDetail {
    private String average_price;
    private String cuisine_text;
    private Double latitude;
    private Double longitude;
    private String restaurant_Licence;
    private String restaurant_address;
    private String restaurant_name;
    private String restaurant_open;
    private String restaurant_phonenumber;
    private String restaurant_prep_time;
    private String restaurant_spotimage;
    private String restaurant_uid;

    public RestaurantDetail() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, 4095, null);
    }

    public final String component1() {
        return this.restaurant_address;
    }

    public final String component10() {
        return this.restaurant_phonenumber;
    }

    public final Double component11() {
        return this.latitude;
    }

    public final Double component12() {
        return this.longitude;
    }

    public final String component2() {
        return this.cuisine_text;
    }

    public final String component3() {
        return this.restaurant_open;
    }

    public final String component4() {
        return this.restaurant_name;
    }

    public final String component5() {
        return this.restaurant_Licence;
    }

    public final String component6() {
        return this.restaurant_spotimage;
    }

    public final String component7() {
        return this.restaurant_prep_time;
    }

    public final String component8() {
        return this.average_price;
    }

    public final String component9() {
        return this.restaurant_uid;
    }

    public final RestaurantDetail copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, Double d, Double d2) {
        return new RestaurantDetail(str, str2, str3, str4, str5, str6, str7, str8, str9, str10, d, d2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RestaurantDetail) {
            RestaurantDetail restaurantDetail = (RestaurantDetail) obj;
            return Intrinsics.areEqual(this.restaurant_address, restaurantDetail.restaurant_address) && Intrinsics.areEqual(this.cuisine_text, restaurantDetail.cuisine_text) && Intrinsics.areEqual(this.restaurant_open, restaurantDetail.restaurant_open) && Intrinsics.areEqual(this.restaurant_name, restaurantDetail.restaurant_name) && Intrinsics.areEqual(this.restaurant_Licence, restaurantDetail.restaurant_Licence) && Intrinsics.areEqual(this.restaurant_spotimage, restaurantDetail.restaurant_spotimage) && Intrinsics.areEqual(this.restaurant_prep_time, restaurantDetail.restaurant_prep_time) && Intrinsics.areEqual(this.average_price, restaurantDetail.average_price) && Intrinsics.areEqual(this.restaurant_uid, restaurantDetail.restaurant_uid) && Intrinsics.areEqual(this.restaurant_phonenumber, restaurantDetail.restaurant_phonenumber) && Intrinsics.areEqual((Object) this.latitude, (Object) restaurantDetail.latitude) && Intrinsics.areEqual((Object) this.longitude, (Object) restaurantDetail.longitude);
        }
        return false;
    }

    public int hashCode() {
        String str = this.restaurant_address;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.cuisine_text;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.restaurant_open;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.restaurant_name;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.restaurant_Licence;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.restaurant_spotimage;
        int hashCode6 = (hashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.restaurant_prep_time;
        int hashCode7 = (hashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.average_price;
        int hashCode8 = (hashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.restaurant_uid;
        int hashCode9 = (hashCode8 + (str9 == null ? 0 : str9.hashCode())) * 31;
        String str10 = this.restaurant_phonenumber;
        int hashCode10 = (hashCode9 + (str10 == null ? 0 : str10.hashCode())) * 31;
        Double d = this.latitude;
        int hashCode11 = (hashCode10 + (d == null ? 0 : d.hashCode())) * 31;
        Double d2 = this.longitude;
        return hashCode11 + (d2 != null ? d2.hashCode() : 0);
    }

    public String toString() {
        return "RestaurantDetail(restaurant_address=" + this.restaurant_address + ", cuisine_text=" + this.cuisine_text + ", restaurant_open=" + this.restaurant_open + ", restaurant_name=" + this.restaurant_name + ", restaurant_Licence=" + this.restaurant_Licence + ", restaurant_spotimage=" + this.restaurant_spotimage + ", restaurant_prep_time=" + this.restaurant_prep_time + ", average_price=" + this.average_price + ", restaurant_uid=" + this.restaurant_uid + ", restaurant_phonenumber=" + this.restaurant_phonenumber + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ')';
    }

    public RestaurantDetail(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, Double d, Double d2) {
        this.restaurant_address = str;
        this.cuisine_text = str2;
        this.restaurant_open = str3;
        this.restaurant_name = str4;
        this.restaurant_Licence = str5;
        this.restaurant_spotimage = str6;
        this.restaurant_prep_time = str7;
        this.average_price = str8;
        this.restaurant_uid = str9;
        this.restaurant_phonenumber = str10;
        this.latitude = d;
        this.longitude = d2;
    }

    public /* synthetic */ RestaurantDetail(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, Double d, Double d2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : str8, (i & 256) != 0 ? null : str9, (i & 512) != 0 ? null : str10, (i & 1024) != 0 ? null : d, (i & 2048) == 0 ? d2 : null);
    }

    public final String getRestaurant_address() {
        return this.restaurant_address;
    }

    public final void setRestaurant_address(String str) {
        this.restaurant_address = str;
    }

    public final String getCuisine_text() {
        return this.cuisine_text;
    }

    public final void setCuisine_text(String str) {
        this.cuisine_text = str;
    }

    public final String getRestaurant_open() {
        return this.restaurant_open;
    }

    public final void setRestaurant_open(String str) {
        this.restaurant_open = str;
    }

    public final String getRestaurant_name() {
        return this.restaurant_name;
    }

    public final void setRestaurant_name(String str) {
        this.restaurant_name = str;
    }

    public final String getRestaurant_Licence() {
        return this.restaurant_Licence;
    }

    public final void setRestaurant_Licence(String str) {
        this.restaurant_Licence = str;
    }

    public final String getRestaurant_spotimage() {
        return this.restaurant_spotimage;
    }

    public final void setRestaurant_spotimage(String str) {
        this.restaurant_spotimage = str;
    }

    public final String getRestaurant_prep_time() {
        return this.restaurant_prep_time;
    }

    public final void setRestaurant_prep_time(String str) {
        this.restaurant_prep_time = str;
    }

    public final String getAverage_price() {
        return this.average_price;
    }

    public final void setAverage_price(String str) {
        this.average_price = str;
    }

    public final String getRestaurant_uid() {
        return this.restaurant_uid;
    }

    public final void setRestaurant_uid(String str) {
        this.restaurant_uid = str;
    }

    public final String getRestaurant_phonenumber() {
        return this.restaurant_phonenumber;
    }

    public final void setRestaurant_phonenumber(String str) {
        this.restaurant_phonenumber = str;
    }

    public final Double getLatitude() {
        return this.latitude;
    }

    public final void setLatitude(Double d) {
        this.latitude = d;
    }

    public final Double getLongitude() {
        return this.longitude;
    }

    public final void setLongitude(Double d) {
        this.longitude = d;
    }
}
