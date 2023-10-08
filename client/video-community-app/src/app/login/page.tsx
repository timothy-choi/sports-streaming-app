import { useRouter } from "next/router";
import { Login } from '../api/index';
import { useState, useEffect } from 'react';
import { TextField, Button } from '@mui/material';

var loginData = {
    username: String,
    password: String
};

export default function LoginPage() {
    var formIsValid = false;
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const router = useRouter();

    useEffect(() => {
        var data = localStorage.getItem("userInfo"); 
        if (data) {
            router.push("/");
        }
        else {
            setIsLoading(false);
        }
    }, [router]);

    const onLoginSubmit = async (loginValues) => {
        setIsLoading(true);
        const res = Login(loginValues);
        if (res.ok) {
            localStorage.setItem("userInfo", JSON.stringify(loginValues));
            router.push("/");
        }
        else {
            setIsLoading(false);
        }
    }

    if (isLoading) {
        return (
            <div id="loader"></div>
        );
    }
    else {
        return (
            <div className="login">
                <form className="loginForm" onSubmit={onLoginSubmit}>
                    <TextField 
                        placeholder = ""
                        label = "Email"
                        required = {true}
                    />
                    <TextField 
                        placeholder = ""
                        label = "Password"
                        required = {true}
                    />
                    <div className="submit">
                        <Button 
                            type="submit"
                            size="large"
                            disabled={!formIsValid}
                        > Submit </Button>
                    </div>
                </form>
            </div>
        );
    }
}