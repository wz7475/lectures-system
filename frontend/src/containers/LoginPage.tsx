import React, {FormEvent, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, AppState} from "../store/store";
import {loginAsync} from "../store/reducers/authReducer";
import EState from "../store/models/common/state";
import APIError from "../store/models/common/apiError";

const LoginPage: React.FC = () => {
    const dispatch: AppDispatch = useDispatch();
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const state = useSelector<AppState, EState>((state) => state.auth.state);
    const error = useSelector<AppState, APIError | null | undefined>((state) => state.auth.error);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        dispatch(loginAsync({
            name: name,
            password: password
        }));
    }

    return (<>
            <h1>Login Page</h1>

            {(state !== EState.Pending) && (
                <form onSubmit={handleSubmit}>
                    <label>
                        Name:
                        <input type="text"
                               value={name}
                               onChange={(event) => setName(event.target.value)}
                        />
                    </label>

                    <label>
                        Password:
                        <input type="password"
                               value={password}
                               onChange={(event) => setPassword(event.target.value)}
                        />
                    </label>

                    {state === EState.Failed && (
                        <>
                            <label>Error {error !== null && error !== undefined && error.code}</label>
                        </>
                    )}

                    <button type="submit">
                        Login
                    </button>
                </form>
            )}
            {state === EState.Pending && (
                <div>Loading...</div>
            )}
        </>
    );
}

export default LoginPage;