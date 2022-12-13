type APIError<T = object> = {
    code: number;
    message: string;
    data?: T | null;
};

export default APIError;