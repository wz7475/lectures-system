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

export const api = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/api"
    }),
    tagTypes: ["Lectures", "SignupLectures"],
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
            })
        }),
        getLectures: builder.query<LecturesResponse, void>({
            query: () => "/lectures",
            providesTags: (result) =>
                result ? result.map(({id}) => ({type: 'Lectures', id})) : ['Lectures'],
        }),
        getLecture: builder.query<Lecture, number>({
            query: (id) => `/lectures/lecture?id=${id}`,
            providesTags: (result, error, id) => [{type: 'Lectures', id}],
        }),
        getSignupLectures: builder.query<LecturesResponse, string>({
            query: (session) => `/lectures/user?sessionKey=${session}`,
            providesTags: (result) =>
                result ? result.map(({id}) => ({type: 'SignupLectures', id})) : ['SignupLectures'],
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
                url: `/lectures?lecture=${data.data?.id}&sessionKey=${data.session}`,
                method: "PUT",
                body: data.data
            }),
            invalidatesTags: ["Lectures"]
        }),
        signupLecture: builder.mutation<ProtectedRequest<number>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/signup?lecture=${data.data}&sessionKey=${data.session}`,
                method: "POST"
            }),
            invalidatesTags: ["SignupLectures"]
        }),
        optoutLecture: builder.mutation<ProtectedRequest<number>, Partial<ProtectedRequest<number>>>({
            query: (data) => ({
                url: `/lectures/output?lecture=${data.data}&sessionKey=${data.session}`,
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
    useSignupLectureMutation,
    useOptoutLectureMutation
} = api;














