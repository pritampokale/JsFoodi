package bugfix.itsolutions.jsfoodi.models;

import androidx.core.app.FrameMetricsAggregator;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: FavoriteRestaurantDetails.kt */
/* loaded from: classes4.dex */
public final class FavoriteRestaurantDetails {
    private String restaurant_address;
    private String restaurant_cuisine;
    private String restaurant_distance;
    private String restaurant_image;
    private String restaurant_name;
    private String restaurant_num;
    private String restaurant_price;
    private String restaurant_time;
    private String restaurant_uid;

    public FavoriteRestaurantDetails() {
        this(null, null, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    public final String component1() {
        return this.restaurant_address;
    }

    public final String component2() {
        return this.restaurant_cuisine;
    }

    public final String component3() {
        return this.restaurant_distance;
    }

    public final String component4() {
        return this.restaurant_image;
    }

    public final String component5() {
        return this.restaurant_name;
    }

    public final String component6() {
        return this.restaurant_num;
    }

    public final String component7() {
        return this.restaurant_price;
    }

    public final String component8() {
        return this.restaurant_time;
    }

    public final String component9() {
        return this.restaurant_uid;
    }

    public final FavoriteRestaurantDetails copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        return new FavoriteRestaurantDetails(str, str2, str3, str4, str5, str6, str7, str8, str9);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FavoriteRestaurantDetails) {
            FavoriteRestaurantDetails favoriteRestaurantDetails = (FavoriteRestaurantDetails) obj;
            return Intrinsics.areEqual(this.restaurant_address, favoriteRestaurantDetails.restaurant_address) && Intrinsics.areEqual(this.restaurant_cuisine, favoriteRestaurantDetails.restaurant_cuisine) && Intrinsics.areEqual(this.restaurant_distance, favoriteRestaurantDetails.restaurant_distance) && Intrinsics.areEqual(this.restaurant_image, favoriteRestaurantDetails.restaurant_image) && Intrinsics.areEqual(this.restaurant_name, favoriteRestaurantDetails.restaurant_name) && Intrinsics.areEqual(this.restaurant_num, favoriteRestaurantDetails.restaurant_num) && Intrinsics.areEqual(this.restaurant_price, favoriteRestaurantDetails.restaurant_price) && Intrinsics.areEqual(this.restaurant_time, favoriteRestaurantDetails.restaurant_time) && Intrinsics.areEqual(this.restaurant_uid, favoriteRestaurantDetails.restaurant_uid);
        }
        return false;
    }

    public int hashCode() {
        String str = this.restaurant_address;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.restaurant_cuisine;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.restaurant_distance;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.restaurant_image;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.restaurant_name;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.restaurant_num;
        int hashCode6 = (hashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.restaurant_price;
        int hashCode7 = (hashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.restaurant_time;
        int hashCode8 = (hashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.restaurant_uid;
        return hashCode8 + (str9 != null ? str9.hashCode() : 0);
    }

    public String toString() {
        return "FavoriteRestaurantDetails(restaurant_address=" + this.restaurant_address + ", restaurant_cuisine=" + this.restaurant_cuisine + ", restaurant_distance=" + this.restaurant_distance + ", restaurant_image=" + this.restaurant_image + ", restaurant_name=" + this.restaurant_name + ", restaurant_num=" + this.restaurant_num + ", restaurant_price=" + this.restaurant_price + ", restaurant_time=" + this.restaurant_time + ", restaurant_uid=" + this.restaurant_uid + ')';
    }

    public FavoriteRestaurantDetails(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.restaurant_address = str;
        this.restaurant_cuisine = str2;
        this.restaurant_distance = str3;
        this.restaurant_image = str4;
        this.restaurant_name = str5;
        this.restaurant_num = str6;
        this.restaurant_price = str7;
        this.restaurant_time = str8;
        this.restaurant_uid = str9;
    }

    public /* synthetic */ FavoriteRestaurantDetails(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : str8, (i & 256) == 0 ? str9 : null);
    }

    public final String getRestaurant_address() {
        return this.restaurant_address;
    }

    public final void setRestaurant_address(String str) {
        this.restaurant_address = str;
    }

    public final String getRestaurant_cuisine() {
        return this.restaurant_cuisine;
    }

    public final void setRestaurant_cuisine(String str) {
        this.restaurant_cuisine = str;
    }

    public final String getRestaurant_distance() {
        return this.restaurant_distance;
    }

    public final String getRestaurant_image() {
        return this.restaurant_image;
    }

    public final void setRestaurant_distance(String str) {
        this.restaurant_distance = str;
    }

    public final void setRestaurant_image(String str) {
        this.restaurant_image = str;
    }

    public final String getRestaurant_name() {
        return this.restaurant_name;
    }

    public final void setRestaurant_name(String str) {
        this.restaurant_name = str;
    }

    public final String getRestaurant_num() {
        return this.restaurant_num;
    }

    public final String getRestaurant_price() {
        return this.restaurant_price;
    }

    public final void setRestaurant_num(String str) {
        this.restaurant_num = str;
    }

    public final void setRestaurant_price(String str) {
        this.restaurant_price = str;
    }

    public final String getRestaurant_time() {
        return this.restaurant_time;
    }

    public final void setRestaurant_time(String str) {
        this.restaurant_time = str;
    }

    public final String getRestaurant_uid() {
        return this.restaurant_uid;
    }

    public final void setRestaurant_uid(String str) {
        this.restaurant_uid = str;
    }
}
