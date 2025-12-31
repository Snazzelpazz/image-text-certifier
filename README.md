# Image Text Certifier

An offline-first Android app that fixes typos and garbled text in images.

## Goal
Input: Any image containing readable text  
Output: The same image with corrected text and no typos

## Core Rules
- Works on any image where text is visually readable
- Uses OCR to detect text regions automatically
- Fixes text deterministically (no AI guessing)
- AI is never allowed after final rendering
- Post-render spellcheck gate enforces correctness
- If text cannot be recovered, the system refuses to guess

## Pipeline
1. OCR detects text regions
2. Text is sanitized using deterministic rules
3. Original text regions are erased
4. Corrected text is re-rendered
5. Image is re-checked to guarantee zero typos

## Status
Project scaffold created.  
Core logic and Android implementation in progress.
