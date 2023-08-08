package bugfix.itsolutions.jsfoodi.models;

import androidx.core.app.FrameMetricsAggregator;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SearchItemDetails.kt */
/* loaded from: classes4.dex */
public final class SearchItemDetails {
    private String average_price;
    private String cuisine_text;
    private String restaurant_address;
    private String restaurant_name;
    private String restaurant_open;
    private String restaurant_phonenumber;
    private String restaurant_prep_time;
    private String restaurant_spotimage;
    private String restaurant_uid;

    public SearchItemDetails() {
        this(null, null, null, null, null, null, null, null, null, FrameMetricsAggregator.EVERY_DURATION, null);
    }

    public final String component1() {
        return this.restaurant_uid;
    }

    public final String component2() {
        return this.restaurant_address;
    }

    public final String component3() {
        return this.restaurant_prep_time;
    }

    public final String component4() {
        return this.cuisine_text;
    }

    public final String component5() {
        return this.restaurant_open;
    }

    public final String component6() {
        return this.restaurant_name;
    }

    public final String component7() {
        return this.average_price;
    }

    public final String component8() {
        return this.restaurant_phonenumber;
    }

    public final String component9() {
        return this.restaurant_spotimage;
    }

    public final SearchItemDetails copy(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        return new SearchItemDetails(str, str2, str3, str4, str5, str6, str7, str8, str9);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SearchItemDetails) {
            SearchItemDetails searchItemDetails = (SearchItemDetails) obj;
            return Intrinsics.areEqual(this.restaurant_uid, searchItemDetails.restaurant_uid) && Intrinsics.areEqual(this.restaurant_address, searchItemDetails.restaurant_address) && Intrinsics.areEqual(this.restaurant_prep_time, searchItemDetails.restaurant_prep_time) && Intrinsics.areEqual(this.cuisine_text, searchItemDetails.cuisine_text) && Intrinsics.areEqual(this.restaurant_open, searchItemDetails.restaurant_open) && Intrinsics.areEqual(this.restaurant_name, searchItemDetails.restaurant_name) && Intrinsics.areEqual(this.average_price, searchItemDetails.average_price) && Intrinsics.areEqual(this.restaurant_phonenumber, searchItemDetails.restaurant_phonenumber) && Intrinsics.areEqual(this.restaurant_spotimage, searchItemDetails.restaurant_spotimage);
        }
        return false;
    }

    public int hashCode() {
        String str = this.restaurant_uid;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.restaurant_address;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.restaurant_prep_time;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.cuisine_text;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.restaurant_open;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.restaurant_name;
        int hashCode6 = (hashCode5 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.average_price;
        int hashCode7 = (hashCode6 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.restaurant_phonenumber;
        int hashCode8 = (hashCode7 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.restaurant_spotimage;
        return hashCode8 + (str9 != null ? str9.hashCode() : 0);
    }

    public String toString() {
        return "SearchItemDetails(restaurant_uid=" + this.restaurant_uid + ", restaurant_address=" + this.restaurant_address + ", restaurant_prep_time=" + this.restaurant_prep_time + ", cuisine_text=" + this.cuisine_text + ", restaurant_open=" + this.restaurant_open + ", restaurant_name=" + this.restaurant_name + ", average_price=" + this.average_price + ", restaurant_phonenumber=" + this.restaurant_phonenumber + ", restaurant_spotimage=" + this.restaurant_spotimage + ')';
    }

    public SearchItemDetails(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        this.restaurant_uid = str;
        this.restaurant_address = str2;
        this.restaurant_prep_time = str3;
        this.cuisine_text = str4;
        this.restaurant_open = str5;
        this.restaurant_name = str6;
        this.average_price = str7;
        this.restaurant_phonenumber = str8;
        this.restaurant_spotimage = str9;
    }

    public /* synthetic */ SearchItemDetails(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6, (i & 64) != 0 ? null : str7, (i & 128) != 0 ? null : str8, (i & 256) == 0 ? str9 : null);
    }

    public final String getRestaurant_uid() {
        return this.restaurant_uid;
    }

    public final void setRestaurant_uid(String str) {
        this.restaurant_uid = str;
    }

    public final String getRestaurant_address() {
        return this.restaurant_address;
    }

    public final void setRestaurant_address(String str) {
        this.restaurant_address = str;
    }

    public final String getRestaurant_prep_time() {
        return this.restaurant_prep_time;
    }

    public final void setRestaurant_prep_time(String str) {
        this.restaurant_prep_time = str;
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

    public final String getAverage_price() {
        return this.average_price;
    }

    public final void setAverage_price(String str) {
        this.average_price = str;
    }

    public final String getRestaurant_phonenumber() {
        return this.restaurant_phonenumber;
    }

    public final void setRestaurant_phonenumber(String str) {
        this.restaurant_phonenumber = str;
    }

    public final String getRestaurant_spotimage() {
        return this.restaurant_spotimage;
    }

    public final void setRestaurant_spotimage(String str) {
        this.restaurant_spotimage = str;
    }
}
