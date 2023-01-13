import {IParentComponentProps} from "../../common/parentComponentProps";
import React from "react";
import "./FetchError.css";

const FetchError: React.FC<IParentComponentProps> = (props) => {
    return (
        <div className="fetch-error">
            {props.children}
        </div>
    );
}

export default FetchError;