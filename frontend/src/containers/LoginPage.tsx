import React, {FormEvent, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, AppState} from "../store/store";
import {loginAsync} from "../store/reducers/authReducer";
import EState from "../store/models/common/state";
import APIError from "../store/models/common/apiError";
import logo from "../logo.svg";
import "./LoginPage.css";
import Loading from "../components/Loading";

const LoginPage: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const state = useSelector<AppState, EState>((state) => state.auth.state);
    const error = useSelector<AppState, APIError | null | undefined>((state) => state.auth.error);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        dispatch(loginAsync({name, password}))
    }

    return (
        <div className="login-page">
            <div className="login-page-content">
                <div className="login-logo">
                    <img src={logo} alt="SWiZZLE Logo"/>
                </div>
                <div className="login-title">SWiZZLE</div>
                {state === EState.Pending ? (
                    <div className="login-form-loading">
                        <Loading/>
                    </div>
                ) : (
                    <form className="login-form" onSubmit={handleSubmit}>
                        <input type="text"
                               value={name}
                               placeholder="Name"
                               onChange={(event) => setName(event.target.value)}
                        />
                        <input type="password"
                               value={password}
                               placeholder="Password"
                               onChange={(event) => setPassword(event.target.value)}
                        />

                        <button className="login-form-button blue" type="submit">Login</button>

                        {state === EState.Failed && error !== null && error !== undefined && (
                            <div className="login-form-error">
                                {error.message}
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