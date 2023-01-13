import React, {FormEvent, useState} from "react";
import {LoginRequest, useRegisterMutation} from "../../store/services/api";
import Loading from "../../components/Loading/Loading";
import logo from "../../logo.svg";
import "./../LoginPage/LoginPage.css";
import {FetchBaseQueryError} from "@reduxjs/toolkit/query";
import APIErrorData from "../../common/apiErrorData";
import {NavLink, useNavigate} from "react-router-dom";

const RegisterPage: React.FC = () => {
    const navigate = useNavigate();

    const [formState, setFormState] = useState<LoginRequest>({
        name: "",
        password: ""
    });

    const [register, {isLoading, isError, error}] = useRegisterMutation();

    const handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) =>
        setFormState((prev) => ({...prev, [name]: value}));

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        await register(formState);
        navigate("/");
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

                        <button className="login-form-button red" type="submit"><i className="icon-log-in"/>Register
                        </button>

                        {isError && (
                            <div className="login-form-error">
                                {errorMessage}
                            </div>
                        )}

                        <div className="login-form-switch">
                            Already have an account? <NavLink to="/" className="nav-link">Sign in</NavLink>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}
export default RegisterPage;