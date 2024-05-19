[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]

<h2 align="center">Cow</h3>
  <p align="center">
    An ASM-like language that compiles to BrainFuck
    <br>
    ·
    <a href="https://github.com/BeefLang/Cow/issues/new?labels=bug&template=bug-report---.md">Report Bug</a>
    <br>
    ·
    <a href="https://github.com/BeefLang/Cow/issues/new?labels=enhancement&template=feature-request---.md">Request Feature</a>
  </p>
</div>

## Usage

```
java -jar Cow-1.0-all.jar
    -verbose <true|false>      Verbosity (optional, default false)
    -input <file>              Input file
    -output <file>             Output file (optional, default output.bf)
```

## Language Doc

### Types

The following types are available:
* ``Code``; syntax: 
```
{
    <commands>
}
```
where a command is either a macro or an unsafe call.
* ``Register``; There are 20 registers, 10 being function registers (like `%eax` in assembly), which can be referenced using `%n` where n is between `0` and `9` (inclusive). The remaining 10 registers are internal registers, referenced using `&n` for n between `0` and `9` (inclusive). These internal registers should not be used (by you), just like internal macros.
* ``Address``; an address pointing to a memory cell. The syntax for this is a hex value, e.g. `0x3` would reference the `4`th memory cell. `_translateAddress(0x0) == 0y20`.
* `@AnyAddress`; an address pointing to a brainfuck cell. The syntax is similar to that of an address, the only difference being that the `x` is replaced with a `y`. `0y0` is the first cell on the tape. This should not be used (by you).
* `BFCode`; syntax:
```
!{
    <brainfuck code>
}
```
note that this type is not available as a macro parameter.

### Macros

Calling a macro:
```
<macro name> <args separated by spaces>;
```

Optionally, parenthesis can be placed.

A macro argument can be one of the following:
* Direct type: an address, an anyaddress, a code block, or a register.
* Indirect type: an argument from the current macro (see below).
* Unsafe call: the return values of the `_translateRegister` and `_translateAddress` unsafes.

Macros beginning with ``@`` are not meant to be used (by you), as they tinker with the brainfuck cells themselves. They are considered internal.

Defining a macro:
```
def <macro name> {
    ...
}
```
or
```
def <macro name>(type1, type2, type3) {
    ...
}
```

Inside a macro body, the arguments supplied by the caller can be referenced using `$n` where `n` is any natural number (starting at 0).

### Unsafes

Unsafes are like macros. The only difference is that they are evaluated at compile-time.

The following unsafes are available:
* `_bf(BFCode)`: write `$0` into the current spot.
* `_sub(Code)`: substitute `$0` into the current spot.
* `_ptrelt(@AnyAddress)`: move `$0` many cells to the right on the tape.
* `_ptrelb(@AnyAddress)`: move `$0` many cells to the left on the tape.
* `_translateAddress(Address)`: get the any address of `$0`. Can be used as a macro argument.
* `_translateRegister(Register)`: get the any address of `$0`. Can be used as a macro argument.
* `_unsafe`: allow unsafe calls.
* `_internal`: allow internal macro calls.

Unsafe calls, just like macro calls, have to end in a semicolon.

## Roadmap

See the [open issues](https://github.com/BeefLang/Cow/issues) for a full list of proposed features (and known issues).

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.


[contributors-shield]: https://img.shields.io/github/contributors/BeefLang/Cow.svg?style=for-the-badge
[contributors-url]: https://github.com/BeefLang/Cow/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/BeefLang/Cow.svg?style=for-the-badge
[forks-url]: https://github.com/BeefLang/Cow/network/members
[stars-shield]: https://img.shields.io/github/stars/BeefLang/Cow.svg?style=for-the-badge
[stars-url]: https://github.com/BeefLang/Cow/stargazers
[issues-shield]: https://img.shields.io/github/issues/BeefLang/Cow.svg?style=for-the-badge
[issues-url]: https://github.com/BeefLang/Cow/issues
[license-shield]: https://img.shields.io/github/license/BeefLang/Cow.svg?style=for-the-badge
[license-url]: https://github.com/BeefLang/Cow/blob/master/LICENSE.txt