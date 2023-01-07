import React, {FormEvent, useState} from "react";
import {LoginRequest, useLoginMutation} from "../../store/services/api";
import Loading from "../../components/Loading/Loading";
import logo from "../../logo.svg";
import "./LoginPage.css";

const LoginPage: React.FC = () => {
    const [formState, setFormState] = useState<LoginRequest>({
        name: "",
        password: ""
    });

    const [login, {isLoading, isError, error}] = useLoginMutation();

    const handleChange = ({target: {name, value}}: React.ChangeEvent<HTMLInputElement>) =>
        setFormState((prev) => ({...prev, [name]: value}));

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        await login(formState)
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

                        <button className="login-form-button blue" type="submit">Login</button>

                        {isError && (
                            <div className="login-form-error">
                                Error
                            </div>
                        )}

                        <div className="login-form-switch">
                            Don't have an account? <a>Sign up</a>
                        </div>
                    </form>
                )}
            </div>
        </div>
    );
}

export default LoginPage;