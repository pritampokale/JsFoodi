package bugfix.itsolutions.jsfoodi.models;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ReviewDetails.kt */
/* loaded from: classes4.dex */
public final class ReviewDetails {
    private String rating;
    private String recommended;
    private String review;
    private String uid;
    private String user_image;
    private String user_name;

    public ReviewDetails() {
        this(null, null, null, null, null, null, 63, null);
    }

    public static /* synthetic */ ReviewDetails copy$default(ReviewDetails reviewDetails, String str, String str2, String str3, String str4, String str5, String str6, int i, Object obj) {
        if ((i & 1) != 0) {
            str = reviewDetails.rating;
        }
        if ((i & 2) != 0) {
            str2 = reviewDetails.review;
        }
        String str7 = str2;
        if ((i & 4) != 0) {
            str3 = reviewDetails.recommended;
        }
        String str8 = str3;
        if ((i & 8) != 0) {
            str4 = reviewDetails.user_name;
        }
        String str9 = str4;
        if ((i & 16) != 0) {
            str5 = reviewDetails.user_image;
        }
        String str10 = str5;
        if ((i & 32) != 0) {
            str6 = reviewDetails.uid;
        }
        return reviewDetails.copy(str, str7, str8, str9, str10, str6);
    }

    public final String component1() {
        return this.rating;
    }

    public final String component2() {
        return this.review;
    }

    public final String component3() {
        return this.recommended;
    }

    public final String component4() {
        return this.user_name;
    }

    public final String component5() {
        return this.user_image;
    }

    public final String component6() {
        return this.uid;
    }

    public final ReviewDetails copy(String str, String str2, String str3, String str4, String str5, String str6) {
        return new ReviewDetails(str, str2, str3, str4, str5, str6);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ReviewDetails) {
            ReviewDetails reviewDetails = (ReviewDetails) obj;
            return Intrinsics.areEqual(this.rating, reviewDetails.rating) && Intrinsics.areEqual(this.review, reviewDetails.review) && Intrinsics.areEqual(this.recommended, reviewDetails.recommended) && Intrinsics.areEqual(this.user_name, reviewDetails.user_name) && Intrinsics.areEqual(this.user_image, reviewDetails.user_image) && Intrinsics.areEqual(this.uid, reviewDetails.uid);
        }
        return false;
    }

    public int hashCode() {
        String str = this.rating;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.review;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.recommended;
        int hashCode3 = (hashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.user_name;
        int hashCode4 = (hashCode3 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.user_image;
        int hashCode5 = (hashCode4 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.uid;
        return hashCode5 + (str6 != null ? str6.hashCode() : 0);
    }

    public String toString() {
        return "ReviewDetails(rating=" + this.rating + ", review=" + this.review + ", recommended=" + this.recommended + ", user_name=" + this.user_name + ", user_image=" + this.user_image + ", uid=" + this.uid + ')';
    }

    public ReviewDetails(String str, String str2, String str3, String str4, String str5, String str6) {
        this.rating = str;
        this.review = str2;
        this.recommended = str3;
        this.user_name = str4;
        this.user_image = str5;
        this.uid = str6;
    }

    public /* synthetic */ ReviewDetails(String str, String str2, String str3, String str4, String str5, String str6, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : str6);
    }

    public final String getRating() {
        return this.rating;
    }

    public final void setRating(String str) {
        this.rating = str;
    }

    public final String getReview() {
        return this.review;
    }

    public final void setReview(String str) {
        this.review = str;
    }

    public final String getRecommended() {
        return this.recommended;
    }

    public final void setRecommended(String str) {
        this.recommended = str;
    }

    public final String getUser_name() {
        return this.user_name;
    }

    public final void setUser_name(String str) {
        this.user_name = str;
    }

    public final String getUser_image() {
        return this.user_image;
    }

    public final void setUser_image(String str) {
        this.user_image = str;
    }

    public final String getUid() {
        return this.uid;
    }

    public final void setUid(String str) {
        this.uid = str;
    }
}
