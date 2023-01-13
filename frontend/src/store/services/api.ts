import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import Lecture from "../../common/Lecture";
import Opinion from "../../common/Opinion";
import {Offer} from "../../common/Offer";


export type ProtectedRequest<T> = {
    session: string,
    data: T
}

// region Authentication
export interface UserResponse {
    sessionKey: string;
    admin: boolean;
    userId: Id;
}

export interface LoginRequest {
    name: string;
    password: string;
}

export interface LogoutRequest {
    sessionKey: string;
}

// endregion

// region Lectures
export type LecturesResponse = Lecture[];
export type Id = number | string;
// endregion

// region Opinions
export type OpinionsResponse = Opinion[];
// endregion

// region Offers
export type OffersResponse = Offer[];

export interface AcceptRequest {
    offerId: Id;
    userId: Id;
}

// endregion

const tagTypes = ["Lectures", "SignupLectures", "Opinions", "Offers"];

export const api = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/api"
    }),
    tagTypes: tagTypes,
    endpoints: (builder) => ({

        // region Authentication
        login: builder.mutation<UserResponse, LoginRequest>({
            query: (credentials) => ({
                url: "user/login",
                method: "POST",
                body: credentials
            })
        }),
        register: builder.mutation<void, LoginRequest>({
            query: (credentials) => ({
                url: "user/register",
                method: "POST",
                body: credentials
            })
        }),
        logout: builder.mutation<void, LogoutRequest>({
            query: (session) => ({
                url: `user/logout?sessionKey=${session.sessionKey}`,
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                },
                body: session
            }),
            invalidatesTags: ["SignupLectures"]
        }),
        // endregion

        // region Lectures
        getLectures: builder.query<LecturesResponse, void>({
            query: () => "/lectures",
            providesTags: ["Lectures"]
        }),
        getLecture: builder.query<Lecture, Id>({
            query: (id) => `/lectures/lecture?id=${id}`,
            providesTags: ["Lectures"]
        }),
        getSignupLectures: builder.query<LecturesResponse, string>({
            query: (session) => `/lectures/user?sessionKey=${session}`,
            providesTags: ["SignupLectures"],
        }),
        addLecture: builder.mutation<ProtectedRequest<Lecture>, Partial<ProtectedRequest<Lecture>>>({
            query: (data) => ({
                url: `/lectures?sessionKey=${data.session}`,
                method: "POST",
                body: data.data
            }),
            invalidatesTags: ["Lectures"]
        }),
        modifyLecture: builder.mutation<ProtectedRequest<Lecture>, Partial<ProtectedRequest<Lecture>>>({
            query: (data) => ({
                url: `/lectures?lectureId=${data.data?.id}&sessionKey=${data.session}`,
                method: "PUT",
                body: data.data
            }),
            invalidatesTags: ["Lectures"]
        }),
        deleteLecture: builder.mutation<void, ProtectedRequest<Id>>({
            query: (data) => ({
                url: `/lectures?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["Lectures"]
        }),
        signupLecture: builder.mutation<ProtectedRequest<Id>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/signup?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "POST"
            }),
            invalidatesTags: ["SignupLectures"]
        }),
        optoutLecture: builder.mutation<ProtectedRequest<Id>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/optout?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["SignupLectures"]
        }),
        getSignedForLecture: builder.query<number[], Id>({
            query: (id) => `/lectures/signed?lectureId=${id}`,
            providesTags: ["SignupLectures"]
        }),
        // endregion

        // region Opinions
        getLectureOpinions: builder.query<OpinionsResponse, Id>({
            query: (id) => `/opinions/lecture?lectureId=${id}`,
            providesTags: ["Opinions"]
        }),
        getOpinion: builder.query<Opinion, Id>({
            query: (id) => `/opinions/opinion?id=${id}`,
            providesTags: ["Opinions"]
        }),
        getUserOpinions: builder.query<OpinionsResponse, string>({
            query: (sessionKey) => `/opinions/user?sessionKey=${sessionKey}`,
            providesTags: ["UserOpinions"]
        }),
        addOpinion: builder.mutation<ProtectedRequest<Opinion>, Partial<ProtectedRequest<Opinion>>>({
            query: (data) => ({
                url: `/opinions?sessionKey=${data.session}`,
                method: "POST",
                body: data.data
            }),
            invalidatesTags: ["Opinions", "UserOpinions"]
        }),
        deleteOpinion: builder.mutation<void, ProtectedRequest<Id>>({
            query: (data) => ({
                url: `/opinions?opinionId=${data.data}&sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["Opinions", "UserOpinions"]
        }),
        // endregion

        // region Offers
        getOffers: builder.query<OffersResponse, string>({
            query: (sessionKey) => `/offer?sessionKey=${sessionKey}`,
            providesTags: ["Offers"]
        }),
        getOffer: builder.query<Offer, ProtectedRequest<Id>>({
            query: (data) => `/offer/${data.data}?sessionKey=${data.session}`,
            providesTags: ["Offers"]
        }),
        addOffer: builder.mutation<ProtectedRequest<Offer>, Partial<ProtectedRequest<Offer>>>({
            query: (data) => ({
                url: `/offers?sessionKey=${data.session}`,
                method: "POST",
                body: data.data
            }),
            invalidatesTags: ["Offers"]
        }),
        deleteOffer: builder.mutation<void, ProtectedRequest<Id>>({
            query: (data) => ({
                url: `/offers/${data.data}?sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["Offers"]
        }),
        acceptOffer: builder.mutation<ProtectedRequest<AcceptRequest>, Partial<ProtectedRequest<AcceptRequest>>>({
            query: (data) => ({
                url: `/offers/accept/${data.data!.offerId}?sessionKey=${data.session}`,
                method: "PUT",
                body: data.data!.userId,
                headers: { "content-type": "application/json" }
            }),
            invalidatesTags: ["Offers"]
        })
        // endregion
    })
})

export const {
    useLoginMutation,
    useRegisterMutation,
    useLogoutMutation,
    useGetLecturesQuery,
    useGetLectureQuery,
    useGetSignupLecturesQuery,
    useAddLectureMutation,
    useModifyLectureMutation,
    useDeleteLectureMutation,
    useSignupLectureMutation,
    useOptoutLectureMutation,
    useGetSignedForLectureQuery,
    useGetLectureOpinionsQuery,
    useGetOpinionQuery,
    useGetUserOpinionsQuery,
    useAddOpinionMutation,
    useDeleteOpinionMutation,
    useGetOffersQuery,
    useGetOfferQuery,
    useAddOfferMutation,
    useDeleteOfferMutation,
    useAcceptOfferMutation
} = api;














