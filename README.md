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

## Stage 4
The program in this stage should work in 3 modes: encode, send and decode.

If the mode is encode then you need to take the text from send.txt, convert it to ready-to-send form (where you have three significant bits per byte) and save the resulted bytes into the file named encoded.txt.

If the mode is send, you should take the file from encoded.txt and simulate the errors in its bytes (1 bit per byte) and save the resulted bytes into the file named received.txt.

If the mode is decode, you should take the file from received.txt and decode it to the normal text. Save the text into the file named decoded.txt.

## Stage 5
In this stage, we will use a more efficient code, the Hamming code.