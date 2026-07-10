import os
import re

def remove_comments_from_file(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Regex logic
    if filepath.endswith('.kt'):
        # 1. Remove block comments /* ... */
        content = re.sub(r'/\*[\s\S]*?\*/', '', content)
        # 2. Remove line comments //... but NOT inside strings like "http://"
        # A simple approach that handles most cases without a full AST parser:
        # We replace // that are not preceded by a colon. 
        # Actually a better regex for Kotlin line comments that aren't URLs:
        content = re.sub(r'(?<!:)//.*', '', content)
    elif filepath.endswith('.html'):
        # Remove HTML comments <!-- ... -->
        content = re.sub(r'<!--[\s\S]*?-->', '', content)
    else:
        return

    # Remove trailing whitespace and multiple blank lines
    lines = [line.rstrip() for line in content.split('\n')]
    cleaned_lines = []
    for line in lines:
        if line == '' and (not cleaned_lines or cleaned_lines[-1] == ''):
            continue # Skip consecutive empty lines
        cleaned_lines.append(line)

    with open(filepath, 'w', encoding='utf-8') as f:
        f.write('\n'.join(cleaned_lines))

def main():
    root_dir = '/home/allenth/AndroidStudioProjects/Portofolio/composeApp/src'
    for subdir, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.kt') or file.endswith('.html'):
                filepath = os.path.join(subdir, file)
                remove_comments_from_file(filepath)

if __name__ == "__main__":
    main()
