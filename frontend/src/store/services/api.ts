import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react";

export interface UserResponse {
    sessionKey: string;
}

export interface LoginRequest {
    name: string;
    password: string;
}

export const api = createApi({
    reducerPath: "authApi",
    baseQuery: fetchBaseQuery({
        baseUrl: "/api"
    }),
    endpoints: (builder) => ({
        login: builder.mutation<UserResponse, LoginRequest>({
            query: (credentials) => ({
                url: "user/login",
                method: "POST",
                body: credentials
            })
        })
    })
})

export const { useLoginMutation } = api;














