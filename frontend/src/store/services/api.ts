import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";
import Lecture from "../models/Lecture";

export interface UserResponse {
    sessionKey: string;
    admin: boolean;
}

export interface LoginRequest {
    name: string;
    password: string;
}

export interface LogoutRequest {
    sessionKey: string;
}

type LecturesResponse = Lecture[];

type ProtectedRequest<T> = {
    session: string,
    data: T
}

type LectureId = number | string;

const tagTypes = ["Lectures", "SignupLectures"];

export const api = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/api"
    }),
    tagTypes: tagTypes,
    endpoints: (builder) => ({
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
        getLectures: builder.query<LecturesResponse, void>({
            query: () => "/lectures",
            providesTags: ["Lectures"]
        }),
        getLecture: builder.query<Lecture, LectureId>({
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
        deleteLecture: builder.mutation<void, ProtectedRequest<LectureId>>({
            query: (data) => ({
                url: `/lectures?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["Lectures"]
        }),
        signupLecture: builder.mutation<ProtectedRequest<LectureId>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/signup?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "POST"
            }),
            invalidatesTags: ["SignupLectures"]
        }),
        optoutLecture: builder.mutation<ProtectedRequest<LectureId>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/optout?lectureId=${data.data}&sessionKey=${data.session}`,
                method: "DELETE"
            }),
            invalidatesTags: ["SignupLectures"]
        })
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
    useOptoutLectureMutation
} = api;














