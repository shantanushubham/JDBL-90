const express = require('express');
const cors = require('cors');

const app = express();
app.use(cors());

app.get('/subtract', (req, res) => {
    const num1 = parseFloat(req.query.num1);
    const num2 = parseFloat(req.query.num2);
    res.json(num1 - num2);
});

const PORT = 3000;
app.listen(PORT, () => {
    console.log(`Subtraction service listening on port ${PORT}`);
});
