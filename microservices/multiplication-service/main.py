from fastapi import FastAPI

app = FastAPI()

@app.get("/multiply")
def multiply(num1: float, num2: float):
    return num1 * num2
