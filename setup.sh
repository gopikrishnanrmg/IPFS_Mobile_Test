#!/bin/bash

# Download necessary modules

pkg install ipfs
pkg install nodejs-lts -y
npm install express
npm install node-cmd
npm install express-fileupload
npm install cors
npm install body-parser
npm install morgan


# IPFS setup
ipfs init


# nodeJS setup

echo "var express = require('express')
var cmd = require('node-cmd')
const fileUpload = require('express-fileupload')
const cors = require('cors')
const bodyParser = require('body-parser')
const morgan = require('morgan')
var fs = require('fs')
var app = express()

app.use(fileUpload({
    createParentPath: true
}))

app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))
app.use(morgan('dev'))


app.post('/upload', async (req, res) => {
        if(!req.files) {
            console.log('no files')
            res.send({
                status: false,
                message: 'No file uploaded'
            })
        } else {
            let file = req.files.file;
            console.log('file is '+file)
            file.mv('./' + file.name)
            console.log('Uploading file')
            res.send(200)
        }
})

app.post('/download',(req,res)=>{
    var file = req.body.file
    console.log('Downloading '+file)
    res.download(file)
})


app.post('/delete',(req,res)=>{
    var file = req.body.file
    fs.unlink(file, (err) => {
        if (err) throw err
        console.log(file+' was deleted')
      })
})

app.get('/execrep',(req,res)=>{
    var command = req.query.com
    cmd.get(
        command,
        (err, data, stderr)=>{
            console.log(data)
            res.send(data)
        }
    )
})


app.listen(9090,()=>{
    console.log('server started')
})" >> service.js


# Start and kill files

echo "ipfs daemon &
node service.js &" >> start.sh

echo "kill \$(ps -e | grep node | awk '{print \$1}')
ipfs shutdown" >> stop.sh
 
chmod 755 start.sh
chmod 755 stop.sh

# Start services

ipfs daemon &

node service.js &
