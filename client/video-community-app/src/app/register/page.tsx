import { useRouter } from "next/router";
import { Register } from '../api/index';
import { useState, useEffect } from 'react';
import { TextField, Button } from '@mui/material';

var registerData = {
    name: String,
    age: Number,
    email: String,
    username: String,
    password: String
};

export default function RegisterPage() {
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

    const onRegisterSubmit = async (registerValues) => {
        setIsLoading(true);
        const res = Register(registerValues);
        if (res.ok) {
            router.push("/login");
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
            <div className="register">
                <form className="registerForm" onSubmit={onRegisterSubmit}>
                    <TextField 
                        placeholder = ""
                        label = "Name"
                        required = {true}
                    />
                    <TextField 
                        placeholder = ""
                        label = "Age"
                        required = {true}
                    />
                    <TextField 
                        placeholder = ""
                        label = "Email"
                        required = {true}
                    />
                    <TextField 
                        placeholder = ""
                        label = "Username"
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