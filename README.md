# Error Correcting Encoder-Decoder https://hyperskill.org/projects/58
## Stage 1
In this stage, you should write a program that creates errors in the input text, 1 random error per 3 symbols. An error means that the character is replaced by another random character. For example, “abc” characters can be “*bc” or “a*c” or “ab*”, where * is a random character. You can replace it with any character but it's recommended to use only uppercase and lowercase English letters, spacebar, and numbers.

## Stage 2
In this stage, you should write a program that:

1. Takes a message the user wants it to send. The input contains a single message.
2. Encode the message by tripling all the symbols.
3. Simulate sending this message via a poor internet connection (in other words, simulate errors).
4. Decode it back again.

## Stage 3
In this stage, you should write a program that reads the text the user wants to send from the `send.txt`, and simulate the sending through a poor internet connection making one-bit errors in every byte of the text.
The received message which contains an error in a single bit in every byte should be saved into `received.txt`.

