Mobile-ATM
========
[![Build Status](https://travis-ci.org/QuarkLabs/mobile-atm.svg?branch=master)](https://travis-ci.org/QuarkLabs/mobile-atm)

**About**
----------------

**Installation**
----------------

**IOTA**
---------

**About IOTA seeds**

######Your IOTA seed is like your private key (similar to bitcoin and ethereum). It is 81 Characters in length. With it you can open your wallet on any computer and generate addresses on which to receive IOTA.
Reference: (https://iota.guide/seed/)

**Generating IOTA seed in different platforms**

Generate your seed on linux:

`cat /dev/urandom |tr -dc A-Z9|head -c${1:-81}`

Generate your seed on Mac:

Open Terminal and type or paste the following then press enter:

`cat /dev/urandom |LC_ALL=C tr -dc 'A-Z9' | fold -w 81 | head -n 1
`
Generate your seed online:

You can generate your seed online using this website (https://ipfs.io/ipfs/QmdqTgEdyKVQAVnfT5iV4ULzTbkV4hhkDkMqGBuot8egfA)

License
=======
The MIT License (MIT)

Copyright (c) 2018 _QuarkLabs_ (https://github.com/QuarkLabs)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
