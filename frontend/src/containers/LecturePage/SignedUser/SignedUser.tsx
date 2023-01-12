import React from "react";
import {Id} from "../../../store/services/api";
import "./SignedUser.css";

interface ISignedUser {
    user: Id;
}

const SignedUser: React.FC<ISignedUser> = (props) => {
    return (
        <div className="signed-user">
            {props.user}
        </div>
    );
}

export default SignedUser;