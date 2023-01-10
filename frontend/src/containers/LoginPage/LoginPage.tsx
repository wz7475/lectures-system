import React, {FormEvent, useState} from "react";
import {LoginRequest, useLoginMutation} from "../../store/services/api";
import Loading from "../../components/Loading/Loading";
import logo from "../../logo.svg";
import "./LoginPage.css";
import {FetchBaseQueryError} from "@reduxjs/toolkit/query";
import APIErrorData from "../../store/models/common/apiErrorData";
import {NavLink} from "react-router-dom";
import {setCredentials} from "../../store/features/authSlice";
import {useDispatch} from "react-redux";
import {AppDispatch} from "../../store/store";

const LoginPage: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();

    const [formState, setFormState] = useState<LoginRequest>({
        name: "",
        password: ""
    });

    const [login, {isLoading, isError, error}] = useLoginMutation();

    const handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) =>
        setFormState((prev) => ({...prev, [name]: value}));

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const authData = await login(formState).unwrap();
        dispatch(setCredentials(authData));
    }

    let errorMessage;
    if (error !== undefined && "status" in error) {
        let err = (error as FetchBaseQueryError);
        let errData = (err.data as APIErrorData);
        if (errData !== undefined && "message" in errData) {
            errorMessage = errData.message;
        } else {
            errorMessage = "Cannot connect to server. Try again later";
        }
    } else {
        errorMessage = "Unknown error. Try again later";
    }

    return (
        <div className="login-page">
            <div className="login-page-content">
                <div className="login-logo">
                    <img src={logo} alt="SWiZLE Logo"/>
                </div>
                <div className="login-title">SWiZLE</div>
                {isLoading ? (
                    <div className="login-form-loading">
                        <Loading/>
                    </div>
                ) : (
                    <form className="login-form" onSubmit={handleSubmit}>
                        <input name="name"
                               type="text"
                               placeholder="Name"
                               onChange={handleChange}
                        />
                        <input name="password"
                               type="password"
                               placeholder="Password"
                               onChange={handleChange}
                        />

                        <button className="login-form-button blue" type="submit"><i className="icon-log-in"/>Login</button>

                        {isError && (
                            <div className="login-form-error">
                                {errorMessage}
                            </div>
                        )}

                        <div className="login-form-switch">
                            Don't have an account? <NavLink to="/register" className="nav-link">Sign up</NavLink>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}
export default LoginPage;