# URCL-VM

## VM ROM format:
- MAGIC (URCL) - 4 Bytes - Big-Endian
- Version Maj - 1 Byte
- Version Min - 1 Byte
- Program Size - 2 Bytes - Big-Endian
- Program - (Program Size) Bytes
- Checksum - 4 Bytes - Big-Endian

## BASE Instruction Format
II OO AA BB CC
II: Instruction Set ID
OO: Opcode
AA: A Byte
BB: B Byte
CC: C Byte
